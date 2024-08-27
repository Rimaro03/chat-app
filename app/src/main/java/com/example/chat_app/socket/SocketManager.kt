package com.example.chat_app.socket

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException

class SocketManager {
    private var socket: Socket? = null;

    private fun setupSocket(username: String) {
        var opt = IO.Options().apply {
            auth = mapOf("username" to username)
        }
        try {
            socket = IO.socket("http://192.168.1.101:3000", opt)
            println("Socket created")
        }
        catch(e: URISyntaxException) {
            println("Error creating socket")
            e.printStackTrace()
        }

        socket?.on(Socket.EVENT_CONNECT) {
            println("Connected to server")
        }

        socket?.on(Socket.EVENT_CONNECT_ERROR) { args ->
            println("Error connecting to server: " + args[0])
        }

        socket?.on(Socket.EVENT_DISCONNECT) {
            println("Disconnected from server")
        }

    }

    fun onMessageReceived(listener: (JSONObject) -> Unit) {
        socket?.on("message") { args ->
            if(args.isNotEmpty()) {
                val message = args[0] as JSONObject
                listener(message)
            }
        }
    }

    fun onUsersReceived(listener: (JSONObject) -> Unit) {
        socket?.on("users") { args ->
            if(args.isNotEmpty()) {
                val users = args[0] as JSONObject
                listener(users)
            }
        }
    }

    fun connect(username: String = "User") {
        setupSocket(username)
        socket?.connect()
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun sendMessage(message: String) {
        socket?.emit("message", message)
    }
}