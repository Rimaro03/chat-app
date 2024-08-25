package com.example.chat_app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chat_app.socket.SocketManager
import com.example.chat_app.ui.ChatViewModel
import com.example.chat_app.ui.screens.LoginScreen
import com.example.chat_app.ui.screens.MessagesScreen

@Composable
fun App(
    navController: NavHostController = rememberNavController(),
    socketManager: SocketManager,
    viewModel: ChatViewModel = ChatViewModel(socketManager),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Routes.valueOf(
        backStackEntry?.destination?.route ?: Routes.Login.name
    )

    Scaffold { innerPadding ->
        val messages by viewModel.messages.collectAsState()

        NavHost(
            navController = navController,
            startDestination = Routes.Login.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ){
            composable(route = Routes.Login.name) {
                LoginScreen(
                    onLogin = {
                        socketManager.connect()
                        navController.navigate(Routes.Messages.name)
                    },
                )
            }
            composable(route = Routes.Messages.name) {
                MessagesScreen(
                    socketManager = socketManager,
                    messages = messages,
                    addMessage = { message -> viewModel.addMessage(message) },
                )
            }
        }
    }
}