package com.example.chat_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chat_app.data.User
import com.example.chat_app.ui.components.MessageStyle
import com.example.chat_app.ui.components.UserStyle

@Composable
fun UsersScreen (
    modifier: Modifier = Modifier,
    users: List<User> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState(), reverseScrolling = true)
            .padding(bottom = 8.dp)
    ) {
        users.forEach { user ->
            UserStyle(
                user = user
            )
        }
    }
}