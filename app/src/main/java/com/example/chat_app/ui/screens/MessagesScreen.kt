package com.example.chat_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MessagesScreen(
    onSendMessage: (String) -> Unit,
    onMessageReceived: ((String)->Unit) -> Unit = {},
    messageList: ArrayList<String> = ArrayList(),
    addMessage: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var message by remember { mutableStateOf("")}
    // new

    fun messageListener(message: String) {
        println("Message received: $message")
        addMessage(message)
    }

    onMessageReceived { msg ->
        messageListener(msg)
    }

    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp),

        ) {
            messageList.forEach {
                Text(it)
            }
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .weight(1f, false),
            verticalAlignment = Alignment.Bottom
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
                        onSendMessage(message)
                    }
                    println("Message sent: $message")
                    addMessage(message)
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
    MessagesScreen(
        onSendMessage = {}
    )
}