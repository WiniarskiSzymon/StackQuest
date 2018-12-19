package com.example.swierk.stackquest.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.swierk.stackquest.R
import dagger.android.AndroidInjection

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}
