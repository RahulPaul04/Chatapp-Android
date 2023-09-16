package com.example.chatclient

import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject


class MyWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        println("WebSocket opened")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        println("Received message: $text")
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

    fun connectToWebSocket() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("ws://192.168.1.3:8000/chats/?phone=7012186017")
            .build()

        val webSocketListener = MyWebSocketListener()
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