package com.example.swierk.stackquest.api

import com.example.swierk.stackquest.model.QueryResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService{

    @GET("search")
    fun getQueryResults(
        @Query("intitle") query :String,
        @Query("site") siteName :String ="stackoverflow",
        @Query("page") pageNumber : Int
    ): Single<QueryResult>
}