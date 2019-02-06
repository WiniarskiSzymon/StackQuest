package com.example.swierk.stackquest.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.swierk.stackquest.api.StackAPI
import com.example.swierk.stackquest.model.QueryResult
import com.example.swierk.stackquest.model.Response
import com.example.swierk.stackquest.model.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class SearchActivityViewModel @Inject constructor(private val stackAPI: StackAPI) : ViewModel(){


    private lateinit var disposable: Disposable


    var responseData: MutableLiveData<QueryResult> = MutableLiveData()
    var responSestatus : MutableLiveData<Response> = MutableLiveData()



     fun getQueryResults(query : String){
        disposable = stackAPI.getQueryResults(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {responSestatus.value=(Response(Status.LOADING))  }
            .subscribe(
                {
                    responSestatus.value = Response(Status.SUCCESS)
                    responseData.value = it
                },
                {
                    responSestatus.value=(Response(Status.ERROR, it.message))
                }

            )
    }


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}