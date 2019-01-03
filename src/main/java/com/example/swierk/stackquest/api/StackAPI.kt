package com.example.swierk.stackquest.api

import com.example.swierk.stackquest.model.QueryResult
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit


class StackAPI (val retrofit: Retrofit){


    fun getQueryResults(query: String): Deferred<QueryResult> {
        val searchService = retrofit.create(SearchService::class.java)
        return searchService.getQueryResults(query)
    }
}