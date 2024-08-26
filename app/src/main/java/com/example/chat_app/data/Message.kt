package com.example.chat_app.data

data class Message (
    var message: String,
    var user: String,
    var timestamp: String,
    var sentByCurrentUser: Boolean,
)