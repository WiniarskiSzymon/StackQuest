package com.example.swierk.stackquest.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver: BroadcastReceiver(){
    companion object {
        val REQUEST_CODE = 12345
        val ACTION = "com.example.swierk.stackquest.background.alarm"
    }


    override fun onReceive(context: Context, intent: Intent) {

    GetLatestQueryService.enqueueWork(context, intent)

    }
}