package com.example.swierk.stackquest.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.swierk.stackquest.api.StackAPI
import com.example.swierk.stackquest.model.QueryResult
import com.example.swierk.stackquest.model.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

import javax.inject.Inject

class SearchActivityViewModel @Inject constructor(private val stackAPI: StackAPI) : ViewModel(){


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