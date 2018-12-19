package com.example.swierk.stackquest.api

import com.example.swierk.stackquest.model.SearchResponse
import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.logging.Logger.getLogger
import javax.inject.Inject

class StackAPI @Inject constructor(private val retrofit: Retrofit){


    fun getQueryResults(query: String): Single<SearchResponse> {
        val searchService = retrofit.create(SearchService::class.java)
        return searchService.getQueryResults(query)
    }
}