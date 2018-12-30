package com.example.swierk.stackquest.background

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.JobIntentService
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.example.swierk.stackquest.R
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GetLatestQueryService: JobIntentService() {

    val CHANNEL_ID= "12345"
    companion object {
        fun enqueueWork(context : Context, intent: Intent){
            enqueueWork(context,GetLatestQueryService::class.java, 1000, intent )
        }
    }

    override fun onHandleWork(intent: Intent) {
        val latestQuery = intent.extras?.get("last_query").toString()
        val title = getFirstQuestionTitleForQuery(latestQuery)
        popNotification(title)
    }

    private fun getFirstQuestionTitleForQuery(lastQuery : String):String{
        val  logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val urlBuilder = HttpUrl.parse("https://api.stackexchange.com/2.2/search")?.newBuilder()
        urlBuilder?.addQueryParameter("intitle", lastQuery)
        urlBuilder?.addQueryParameter("site", "stackoverflow")

        val url = urlBuilder?.build().toString()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val json = JSONObject(response.body()?.string())
        val title = json.getJSONArray("items").getJSONObject(0).getString("title")

       return title
    }

    private fun popNotification(questionTitle:String){
        createNotificationChannel()
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.notification_not_round)
            .setContentTitle("StackQuest")
            .setContentText(questionTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(9999, mBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "StackQueryChannel"
            val descriptionText = "Channel for stack question"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}