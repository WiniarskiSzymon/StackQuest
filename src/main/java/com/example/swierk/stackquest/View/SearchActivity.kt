package com.example.swierk.stackquest.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.swierk.stackquest.R
import com.jakewharton.rxbinding.widget.RxSearchView

import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

    }
    private fun initSearch(){
        RxSearchView.queryTextChanges(search_query)
            .debounce(1, TimeUnit.SECONDS)
            .filter { it.length>3 }
            .subscribe{vm.searchQuery(it)}


    }


}
