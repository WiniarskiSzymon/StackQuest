package com.example.swierk.stackquest.api

import com.example.swierk.stackquest.model.QueryResult
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class StackAPI @Inject constructor(private val retrofit: Retrofit){


    fun getQueryResults(query: String, pageNumber : Int): Single<QueryResult> {
        val searchService = retrofit.create(SearchService::class.java)
        return searchService.getQueryResults(query, pageNumber = pageNumber)
    }
}