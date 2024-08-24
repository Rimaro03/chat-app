package com.example.chat_app.ui

import io.socket.client.Socket

data class ChatUiState (
    var messages: ArrayList<String> = ArrayList(),
)