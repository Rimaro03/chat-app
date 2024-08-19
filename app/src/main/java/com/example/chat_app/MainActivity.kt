package com.example.chat_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.chat_app.App
import com.example.chat_app.socket.SocketManager
import com.example.chat_app.ui.theme.ChatappTheme

class MainActivity : ComponentActivity() {
    private val socketManager = SocketManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ChatappTheme {
                App(
                    socketManager = socketManager
                )
            }
        }

        socketManager.connect()

        socketManager.onMessageReceived { message ->
            println("Received message: $message")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socketManager.disconnect()
    }
}