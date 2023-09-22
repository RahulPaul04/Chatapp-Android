package com.example.chatclient

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseInstanceIDService : FirebaseMessagingService(){
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(ContentValues.TAG,"Refreshed token:$token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG,"Frome :${message.from}")

        if (message.data.isNotEmpty()){
            Log.d(TAG,"Message data payload :${message.data}")
        }

        message.notification?.let{
            Log.d(TAG,"Message Notification Body: ${it.body}")
        }
    }

}