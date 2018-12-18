package com.example.swierk.stackquest.api

import com.example.swierk.stackquest.model.SearchResponse
import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Logger.getLogger

class StackApi{


    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val  retrofit = Retrofit.Builder()
        .baseUrl("https://api.stackexchange.com/2.2")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun getQueryResults(query: String): Single<SearchResponse> {
        val searchService = retrofit.create(SearchService::class.java)
        return searchService.getQueryResults(query)
    }
}