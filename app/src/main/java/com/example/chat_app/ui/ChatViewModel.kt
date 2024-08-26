package com.example.chat_app.ui

import androidx.lifecycle.ViewModel
import com.example.chat_app.data.Message
import com.example.chat_app.socket.SocketManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject

class ChatViewModel(private val socketManager: SocketManager): ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    init {
        socketManager.onMessageReceived { res: JSONObject ->
            val message = Message(
                message = res.getString("message"),
                user = res.getString("user"),
                timestamp = res.getString("timestamp"),
                sentByCurrentUser = false
            )
            addMessage(message)
        }
    }

    fun addMessage(message: Message) {
        _messages.update { messages ->
            messages + message
        }
    }

    override fun onCleared() {
        super.onCleared()
        socketManager.disconnect()
    }
}