package com.example.swierk.stackquest.View

import android.app.AlarmManager
import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.swierk.stackquest.*
import com.example.swierk.stackquest.background.AlarmReceiver
import com.example.swierk.stackquest.model.QueryResult
import com.example.swierk.stackquest.model.Question
import com.example.swierk.stackquest.model.Response
import com.example.swierk.stackquest.model.Status
import com.example.swierk.stackquest.viewModel.SearchActivityViewModel
import com.example.swierk.stackquest.viewModel.SearchActivityViewModelFactory
import com.jakewharton.rxbinding.widget.RxSearchView

import dagger.android.AndroidInjection
import rx.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    @Inject
    lateinit var searchViewModelFactory: SearchActivityViewModelFactory

    private val searchActivityViewModel by lazy {
        ViewModelProviders.of(this, searchViewModelFactory)
            .get(SearchActivityViewModel::class.java)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private var questionList: MutableList<Question> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        swiperefresh.setOnRefreshListener {
            if(search_query.query.isNotEmpty()) {
                searchActivityViewModel.getQueryResults(search_query.query.toString())
            }
            else swiperefresh.isRefreshing = false
        }

        val viewManager = LinearLayoutManager(this)
        viewAdapter = SearchListAdapter(questionList) { question: Question ->
            showQuestionDetails(question)
        }
        recyclerView = findViewById<RecyclerView>(R.id.search_list_recycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addOnScrollListener(
                EndlessScrollListener(
                    {pageNumber : Int ->searchActivityViewModel.getQueryResults(search_query.query.toString(), pageNumber)}
                    ,viewManager))
        }

        observeResponseStatus()
        observeResponseData()
        initSearchBar()
    }

    private fun showQuestionDetails(question : Question){
        val uris = Uri.parse(question.link)
        val intent = Intent(Intent.ACTION_VIEW, uris)
        startActivity(intent)
    }

    private fun observeResponseStatus() {
        searchActivityViewModel.responSestatus.observe(this, Observer<Response> {
            when (it?.status) {
                Status.SUCCESS -> {
                    swiperefresh.isRefreshing = false
                }
                Status.LOADING ->swiperefresh.isRefreshing = true
                Status.ERROR -> {
                    swiperefresh.isRefreshing = false
                    showErrorAlert(it.errorMessage)
                }
            }
        }
        )
    }

    private fun observeResponseData() {
        searchActivityViewModel.responseData.observe(this, Observer<List<Question>> {
            if (it != null) {
                updateAdapterWithData(it)
                }
            })
        }

    private fun updateAdapterWithData(queryResult: List<Question>){
            questionList.clear()
            questionList.addAll(queryResult)
            viewAdapter.notifyDataSetChanged()

    }

    private fun showErrorAlert(errorMessage: String?) {
        val alert = AlertDialog.Builder(this)

        with(alert) {
            setTitle(R.string.error_alert_title)
            setMessage(R.string.error_alert_message)
            setPositiveButton(R.string.error_alert_ok) { dialog, _ ->
                dialog.dismiss()
            }
            setNegativeButton(R.string.error_alert_more_info) { dialog, _ ->
                dialog.dismiss()
                showDetailedErrorAlert(errorMessage)
            }
        }
        val dialog = alert.create()
        dialog.show()
    }

    private fun showDetailedErrorAlert(errorMessage: String?) {
        val alert = AlertDialog.Builder(this)

        with(alert) {
            setTitle(R.string.error_alert_title)
            setMessage(errorMessage)
            setPositiveButton(R.string.error_alert_ok) { dialog, _ ->
                dialog.dismiss()
            }
        }
        val dialog = alert.create()
        dialog.show()
    }

    private fun initSearchBar() {
        search_query.setIconifiedByDefault(false)
        RxSearchView.queryTextChanges(search_query)
            .debounce(400, TimeUnit.MILLISECONDS)
            .skip(1)
            .filter { it.isNotEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .distinctUntilChanged()
            .subscribe {
                searchActivityViewModel.getQueryResults(it.toString())
            }
    }

    override fun onStart() {
        super.onStart()
        cancelBackroundQuery()
    }

    private fun cancelBackroundQuery(){
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarm = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pendingIntent)
    }

     override fun onStop() {
        super.onStop()
         if(search_query.query.isNotEmpty()) {
             scheduleBackroundQuery(search_query.query.toString())
         }
    }

    private fun scheduleBackroundQuery(lastQuery: String){
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        intent.putExtra("last_query", lastQuery)
        val pendingIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val startTime = System.currentTimeMillis()
        val alarm = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intervalInMiliseconds = 2L*60*1000
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, startTime,
            intervalInMiliseconds, pendingIntent)
    }




}



