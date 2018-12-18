package com.example.swierk.stackquest.api

import com.example.swierk.stackquest.model.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService{

    @GET("/search")
    fun getQueryResults(
        @Query("intitle") query :String,
    ): Single<SearchResponse>
}