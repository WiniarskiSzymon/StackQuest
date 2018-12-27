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
import android.support.v7.recyclerview.R.attr.layoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.swierk.stackquest.*
import com.example.swierk.stackquest.background.AlarmReceiver
import com.example.swierk.stackquest.model.QueryResult
import com.example.swierk.stackquest.model.Question
import com.example.swierk.stackquest.model.Response
import com.jakewharton.rxbinding.widget.RxSearchView

import dagger.android.AndroidInjection
import rx.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_search.*
import java.util.Collections.addAll
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
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var questionList: MutableList<Question> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        cancelBackroundQuery()

        swiperefresh.setOnRefreshListener {
            if(search_query.query.isNotEmpty()) {
                searchActivityViewModel.getQueryResults(search_query.query.toString())
            }
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = SearchListAdapter(questionList){question : Question ->  showQuestionDetails( question )}
        recyclerView = findViewById<RecyclerView>(R.id.search_list_recycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        observeSearchResponseLiveData()
        initSearchBar()
    }

    private fun showQuestionDetails(question : Question){
        val uris = Uri.parse(question.link)
        val intent = Intent(Intent.ACTION_VIEW, uris)
        startActivity(intent)
    }

    private fun observeSearchResponseLiveData() {
        searchActivityViewModel.searchResponse.observe(this, Observer<Response> {
            when (it?.status) {
                Response.Status.SUCCESS -> {
                    updateAdapterWithData(it)
                    swiperefresh.isRefreshing = false
                }
                Response.Status.LOADING ->swiperefresh.isRefreshing = true
                Response.Status.ERROR -> {
                    swiperefresh.isRefreshing = false
                    updateAdapterWithData(it)
                    it.errorMessage?.toast(this)
                }
            }
        }
        )
    }

    private fun updateAdapterWithData(response: Response){
        if (response.data != null) {
            questionList.clear()
            questionList.addAll(response.data.items)
            viewAdapter.notifyDataSetChanged()
        }
        }



    private fun initSearchBar() {
        RxSearchView.queryTextChanges(search_query)
            .debounce(1, TimeUnit.SECONDS)
            .skip(1)
            .filter { it.isNotEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .distinctUntilChanged()
            .subscribe {
                searchActivityViewModel.getQueryResults(it.toString())
            }
        search_query.setOnSearchClickListener {
            if(search_query.query.isNotEmpty()) {
            searchActivityViewModel.getQueryResults(search_query.query.toString())
        }}

    }

     override fun onDestroy() {
        super.onDestroy()
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
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, startTime,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent)
    }

    private fun cancelBackroundQuery(){
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarm = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pendingIntent)
    }



}



