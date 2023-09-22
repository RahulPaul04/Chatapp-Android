package com.example.chatclient

import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context.NOTIFICATION_SERVICE
import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject






class MyWebSocketListener(val chatViewModel: ChatViewModel) : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        println("WebSocket opened")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        println("Received message: $text")
        val jsonObject = JSONObject(text)
        val message = jsonObject.getString("message")

        chatViewModel.rowList.add(RowData( message, false,color = Color.LightGray))



    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        println("WebSocket closed: $code, $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        println("WebSocket failure: ${t.message}")
    }
}



// Call connectToWebSocket to initiate the WebSocket connection
class ChatViewModel :ViewModel() {
    private val client = OkHttpClient()
    private lateinit var webSocket: WebSocket
    val  rowList =  mutableStateListOf<RowData>()

    fun connectToWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("ws://192.168.1.5:8000/chats/?phone=7012186017")
            .build()

        val webSocketListener = MyWebSocketListener(this)
        webSocket = client.newWebSocket(request, webSocketListener)

        // Optionally, you can also enqueue messages to send
        // webSocket.send("Hello, server!")
    }
    fun sendMessage(msg:String){
        //Send Message Logic
        val jsonObject = JSONObject()
        jsonObject.put("message",msg)
        jsonObject.put("sender",7012186017)
        jsonObject.put("receiver",9496428355)
        val jsonString = jsonObject.toString()
        webSocket.send(jsonString)

    }

}