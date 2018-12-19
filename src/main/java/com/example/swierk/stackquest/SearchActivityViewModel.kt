package com.example.swierk.stackquest

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.swierk.stackquest.api.StackAPI
import com.example.swierk.stackquest.model.SearchResponse
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchActivityViewModel @Inject constructor(private val stackAPI: StackAPI) : ViewModel(){


    private lateinit var disposable: Disposable
    val loadingBarVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()



    private fun getQueryResults(query : String){
        disposable = stackAPI.getQueryResults(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onQueruSearchingStart() }
            .doOnSuccess { onQuerySearchingFinished(it) }
            .subscribe()
    }

    private fun onQueruSearchingStart(searchResponse: SearchResponse){
        loadingBarVisibility.value = View.VISIBLE
        errorMessage.value = null
    }


    }
    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}