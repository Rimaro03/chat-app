package com.example.chat_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app.data.Message

@Composable
fun MessageStyle (
    modifier: Modifier = Modifier,
    message: Message
) {
    val backgroundColor =
        if (message.sentByCurrentUser) {
            if(isSystemInDarkTheme()) Color(0xFF005B4A) else Color(0xFFDCF8C6)
        } else MaterialTheme.colorScheme.primaryContainer
    val alignment = if (message.sentByCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
    val padding = if (message.sentByCurrentUser) PaddingValues(start = 64.dp, end = 8.dp, top = 8.dp) else PaddingValues(start = 8.dp, end = 64.dp, top = 8.dp)

    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        contentAlignment = alignment
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .padding(8.dp)
        ) {
            Text(
                text = message.user,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.message,
                fontSize = 16.sp,
                color = MaterialTheme.typography.bodyMedium.color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.timestamp,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun MessageStylePreview() {
    MessageStyle(
        message = Message("Hello", "User", "12:00", false)
    )
}