package com.example.swierk.stackquest.background

import android.content.Context
import android.content.Intent
import android.support.v4.app.JobIntentService

class GetLatestQueryService: JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val lastQuery = intent.extras?.get("last_query").toString()
}
    companion object {
        fun enqueueWork(context : Context, intent: Intent){
            enqueueWork(context,GetLatestQueryService::class.java, 1000, intent )

        }
    }
}