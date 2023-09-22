package com.example.chatclient

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationHelper(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "Chat_app_notifications"
        const val CHANNEL_NAME = "chat Notifications"
        const val CHANNEL_DESCRIPTION = "Get Incoming Message Notifications"
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
