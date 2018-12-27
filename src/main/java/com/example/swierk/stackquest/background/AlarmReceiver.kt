package com.example.swierk.stackquest.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver: BroadcastReceiver(){
    companion object {
        val REQUEST_CODE = 12345
        val ACTION = "com.example.swierk.stackquest.background.alarm"
    }


    override fun onReceive(context: Context?, intent: Intent?) {

        val i = Intent(context, GetLatestQueryService::class.java)
        val lastQuery = intent?.extras?.get("last_query").toString()
        i.putExtra("last_query",lastQuery)
        context?.startService(i)
    }
}