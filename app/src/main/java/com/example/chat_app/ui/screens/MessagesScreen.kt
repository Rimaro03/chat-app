package com.example.chat_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chat_app.data.Message
import com.example.chat_app.socket.SocketManager
import com.example.chat_app.ui.components.MessageStyle

@Composable
fun MessagesScreen(
    socketManager: SocketManager = SocketManager(),
    messages: List<Message> = emptyList(),
    addMessage: (Message) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var message by remember { mutableStateOf("")}

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // Messages
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 8.dp)
        ) {
            messages.forEach { message ->
                MessageStyle(
                    message = message
                )
            }
        }

        // Message input
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = {message = it},
                placeholder = { Text("Message") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    if(message.isNotEmpty()) {
                        socketManager.sendMessage(message)
                    }
                    println("Message sent: $message")
                    val msg = Message(
                        message = message,
                        user = "You",
                        timestamp = "12:00",
                        sentByCurrentUser = true
                    )
                    addMessage(msg)
                    message = ""
                },
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }
    }
}

@Preview
@Composable
fun MessagesScreenPreview() {
    MessagesScreen()
}