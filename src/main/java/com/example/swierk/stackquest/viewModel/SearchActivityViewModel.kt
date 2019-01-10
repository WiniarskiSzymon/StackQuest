package com.example.swierk.stackquest.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.swierk.stackquest.api.StackAPI
import com.example.swierk.stackquest.model.QueryResult
import com.example.swierk.stackquest.model.Question
import com.example.swierk.stackquest.model.Response
import kotlinx.coroutines.*


class SearchActivityViewModel (private val stackAPI: StackAPI) : ViewModel(){


    var searchResponse: MutableLiveData<Response> = MutableLiveData()
    private var cachedQueryResult : MutableList<Question>? = null
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)


     fun getQueryResults(query : String){
         searchResponse.value=(Response.loading())
         uiScope.launch{
             val queryRequest = stackAPI.getQueryResults(query, page=1)
             try {
                 cachedQueryResult= queryRequest.await().items.toMutableList()
             } catch (e: Throwable) {
                 searchResponse.value=(Response.error(e.message ))
             }
             finally {
                 searchResponse.value=(Response.success(cachedQueryResult))
             }
         }
    }

    fun getMoreForQuery(query : String, page : Int){
            searchResponse.value=(Response.loading())
            uiScope.launch{
                val queryRequest = stackAPI.getQueryResults(query, page)
                try {
                    cachedQueryResult?.addAll(queryRequest.await().items)
                } catch (e: Throwable) {
                    searchResponse.value=(Response.error(e.message ))
                }
                finally {
                    searchResponse.value=(Response.success(cachedQueryResult))
                }
            }
        }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}