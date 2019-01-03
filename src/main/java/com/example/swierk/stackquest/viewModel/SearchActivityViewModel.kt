package com.example.swierk.stackquest.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.swierk.stackquest.api.StackAPI
import com.example.swierk.stackquest.model.QueryResult
import com.example.swierk.stackquest.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class SearchActivityViewModel (val stackAPI: StackAPI) : ViewModel(){


    var searchResponse: MutableLiveData<Response> = MutableLiveData()
    private var cachedQueryResult : QueryResult? = null


     fun getQueryResults(query : String){
         searchResponse.value=(Response.loading())
         GlobalScope.launch(Dispatchers.Main) {
             val queryRequest = stackAPI.getQueryResults(query)
             try {
                 cachedQueryResult = queryRequest.await()
             } catch (e: Throwable) {
                 searchResponse.value=(Response.error(e.message ))
             }
             finally {
                 searchResponse.value=(Response.success(cachedQueryResult))
             }
         }
    }
}