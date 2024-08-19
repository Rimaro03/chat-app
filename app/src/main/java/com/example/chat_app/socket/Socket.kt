package com.example.chat_app.socket

import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException

class SocketManager {
    private var socket: Socket? = null

    init {
        try {
            socket = IO.socket("http://192.168.1.6:3000")
            println("Socket created")
        }
        catch(e: URISyntaxException) {
            println("Error creating socket")
            e.printStackTrace()
        }
    }

    fun connect() {
        socket?.connect()
        println("Connected to server")
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun onMessageReceived(listener: (String) -> Unit) {
        socket?.on("message") { args ->
            val message = args[0] as String
            listener.invoke(message)
        }
    }

    fun sendMessage(message: String) {
        socket?.emit("message", message)
    }
}