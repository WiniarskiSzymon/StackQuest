package com.example.swierk.stackquest.View

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.recyclerview.R.attr.layoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.swierk.stackquest.*
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

    private val searchActivityViewModel by lazy{ ViewModelProviders.of(this, searchViewModelFactory)
            .get(SearchActivityViewModel::class.java)}

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var questionList : MutableList<Question> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        viewManager = LinearLayoutManager(this)
        viewAdapter = SearchListAdapter(questionList)

        recyclerView = findViewById<RecyclerView>(R.id.search_list_recycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        initSearch()
    }


    private fun initSearch(){
        RxSearchView.queryTextChanges(search_query)
            .debounce(1, TimeUnit.SECONDS)
            .filter { !it.isEmpty() }
            .onBackpressureLatest()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                searchActivityViewModel.searchQuery(it.toString()).observe(this, Observer<Response>{
                if (it?.status == Response.Status.SUCCESS){
                        if (it.data != null) {
                            questionList.removeAll { it !=null }
                            questionList.addAll(it.data.items)
                            viewAdapter.notifyDataSetChanged()
                        }
                    }

                }
            )}
    }


}
