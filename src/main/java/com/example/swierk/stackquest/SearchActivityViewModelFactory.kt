package com.example.swierk.stackquest

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.swierk.stackquest.api.StackAPI
import javax.inject.Inject

class SearchActivityViewModelFactory @Inject constructor(
    private val stackAPI: StackAPI
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchActivityViewModel::class.java) -> {
                SearchActivityViewModel(stackAPI) as T
            }
            else -> throw IllegalArgumentException(
                "${modelClass.simpleName} is an unknown type of view model"
            )
        }
    }
}