package com.example.chat_app.ui

import androidx.lifecycle.ViewModel
import com.example.chat_app.socket.SocketManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel(private val socketManager: SocketManager): ViewModel() {
    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    init {
        socketManager.onMessageReceived { message ->
            addMessage(message)
        }
    }

    fun addMessage(message: String) {
        _messages.update { messages ->
            messages + message
        }
    }

    override fun onCleared() {
        super.onCleared()
        socketManager.disconnect()
    }
}