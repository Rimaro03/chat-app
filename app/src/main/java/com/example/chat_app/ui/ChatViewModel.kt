package com.example.chat_app.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.chat_app.data.Message
import com.example.chat_app.data.User
import com.example.chat_app.socket.SocketManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject

class ChatViewModel(private val socketManager: SocketManager): ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

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

        socketManager.onUsersReceived { res: JSONObject ->
            val users = res.get("users")
            val typeToken = object : TypeToken<List<User>>() {}.type
            val usersList = Gson().fromJson<List<User>>(users.toString(), typeToken)
            _users.update { usersList }
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