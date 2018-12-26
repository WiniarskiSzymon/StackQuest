package com.example.swierk.stackquest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.swierk.stackquest.api.StackAPI
import com.example.swierk.stackquest.model.QueryResult
import com.example.swierk.stackquest.model.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class SearchActivityViewModel @Inject constructor(private val stackAPI: StackAPI) : ViewModel(){


    private lateinit var disposable: Disposable


    var searchResponse: MutableLiveData<Response> = MutableLiveData()
    private var cachedQueryResult : QueryResult? = null


     fun getQueryResults(query : String){
        disposable = stackAPI.getQueryResults(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {searchResponse.value=(Response.loading())  }
            .subscribe(
                {
                    searchResponse.value=(Response.success(it))
                    cachedQueryResult = it
                },
                {
                    searchResponse.value=(Response.error(it.message ))
                    searchResponse.value=(Response.success(cachedQueryResult))
                }

            )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}