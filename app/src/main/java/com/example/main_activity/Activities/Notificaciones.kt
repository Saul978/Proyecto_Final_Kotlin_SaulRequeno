package com.example.main_activity.Activities

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

public   val canal_1_ID:String = "canal1"
public class Notificaciones:Application(){
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
       var canal_1: NotificationChannel= NotificationChannel(
           canal_1_ID,
           "canal 1",
           NotificationManager.IMPORTANCE_HIGH
       )
        var manager:NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        manager.createNotificationChannel(canal_1)

    }
    companion object{
        fun canal():String= canal_1_ID
    }
}