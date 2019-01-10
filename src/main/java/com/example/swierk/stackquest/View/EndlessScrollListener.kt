package com.example.swierk.stackquest.View

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class EndlessScrollListener(
    val getQueryFunc : (page : Int)-> Unit,
    val layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener(){

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 5
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private val amountOfItemsInOneRequest =30

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if( layoutManager.itemCount == amountOfItemsInOneRequest){
            previousTotal =0
        }

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
                getQueryFunc((totalItemCount/amountOfItemsInOneRequest) +1)
                loading = true
            }
        }
    }
}