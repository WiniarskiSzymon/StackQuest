package com.example.swierk.stackquest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.swierk.stackquest.api.StackAPI
import com.example.swierk.stackquest.model.SearchResponse
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchActivityViewModel @Inject constructor(private val stackAPI: StackAPI) : ViewModel(){


    private lateinit var disposable: Disposable


    private lateinit var searchResponse: MutableLiveData<SearchResponse>

    fun searchQuery(query : String): LiveData<SearchResponse> {
        if (!::searchResponse.isInitialized) {
            searchResponse = MutableLiveData()
            getQueryResults(query)
        }
        return searchResponse
    }

    private fun getQueryResults(query : String){
        disposable = stackAPI.getQueryResults(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { searchResponse.value = it }
            .subscribe()
    }


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}