package com.example.chat_app.socket

import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.channels.Channel
import java.net.URISyntaxException

class SocketManager {
    private lateinit var socket: Socket
    private val _messageChannel = Channel<String> (capacity = Channel.UNLIMITED) {
        Channel.BUFFERED
    }

    val messageChannel = _messageChannel

    init {
        try {
            socket = IO.socket("http://192.168.1.101:3000")
            println("Socket created")
            setupSocket()
        }
        catch(e: URISyntaxException) {
            println("Error creating socket")
            e.printStackTrace()
        }
    }

    private fun setupSocket() {
        socket.on(Socket.EVENT_CONNECT) {
            println("Connected to server")
        }

        socket.on(Socket.EVENT_CONNECT_ERROR) {
            println("Error connecting to server")
        }

        socket.on(Socket.EVENT_DISCONNECT) {
            println("Disconnected from server")
        }

    }

    fun onMessageReceived(listener: (String) -> Unit) {
        socket.on("message") { args ->
            if(args.isNotEmpty()) {
                val message = args[0] as String
                listener(message)
            }
        }
    }

    fun connect() {
        socket.connect()
    }

    fun disconnect() {
        socket.disconnect()
    }

    fun sendMessage(message: String) {
        socket.emit("message", message)
    }
}