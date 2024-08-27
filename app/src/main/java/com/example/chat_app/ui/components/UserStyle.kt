package com.example.chat_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app.data.User

@Composable
fun UserStyle (
    modifier: Modifier = Modifier,
    user: User = User("123", "Antonio")
) {
    Row (
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            Icons.Default.AccountCircle,
            contentDescription = "User",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column (modifier = Modifier.weight(1f)) {
            Text(
                text = user.username,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            //Last message
            user.lastMessage?.let {
                Text(
                    text = it,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Online Indicator
            if (user.isOnline) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color.Green)
                )
            }
        }
    }
    Column {
        Text(text = user.username)

    }
}

@Preview
@Composable
fun UserStylePreview() {
    UserStyle()
}