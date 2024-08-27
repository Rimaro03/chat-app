package com.example.chat_app.data

data class User (
    val userID: String,
    val username: String,
    val isOnline: Boolean = false,
    val lastMessage: String = "Hello World!"
)