package com.example.swierk.stackquest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.swierk.stackquest.api.StackAPI
import com.example.swierk.stackquest.model.Response
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SearchActivityViewModel @Inject constructor(private val stackAPI: StackAPI) : ViewModel(){


    private lateinit var disposable: Disposable


    private lateinit var searchResponse: MutableLiveData<Response>

    fun searchQuery(query : String): LiveData<Response> {
        if (!::searchResponse.isInitialized) {
            searchResponse = MutableLiveData()
        }
        getQueryResults(query)
        return searchResponse
    }

    private fun getQueryResults(query : String){
        disposable = stackAPI.getQueryResults(query)
            .subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {searchResponse.postValue(Response.loading())  }
            .doOnSuccess { searchResponse.postValue(Response.success(it)) }
            .doOnError { searchResponse.postValue(Response.error()) }
            .subscribe()
    }


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}