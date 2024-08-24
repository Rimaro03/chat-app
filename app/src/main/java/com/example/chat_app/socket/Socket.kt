package com.example.chat_app.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketManager {
    private var socket: Socket? = null

    init {
        try {
            socket = IO.socket("http://192.168.1.101:3000")
            println("Socket created")
        }
        catch(e: URISyntaxException) {
            println("Error creating socket")
            e.printStackTrace()
        }
    }

    fun connect() {
        socket?.connect()
    }

    fun onConnect() {
        socket?.on(Socket.EVENT_CONNECT) {
            println("Connected to server")
        }
    }

    fun onConnectErr() {
        socket?.on(Socket.EVENT_CONNECT_ERROR) {
            println("Error connecting to server")
        }
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun onMessageReceived(listener: (String) -> Unit) {
        socket?.on("message") { args ->
            val message = args[0] as String
            listener(message)
        }
    }

    fun sendMessage(message: String) {
        socket?.emit("message", message)
    }
}