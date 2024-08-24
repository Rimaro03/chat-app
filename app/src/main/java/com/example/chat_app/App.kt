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
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun App(
    viewModel: ChatViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    socketManager: SocketManager
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Routes.valueOf(
        backStackEntry?.destination?.route ?: Routes.Login.name
    )

    Scaffold { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

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
                        socketManager.onConnect()
                        socketManager.onConnectErr()
                        navController.navigate(Routes.Messages.name)
                    },
                )
            }
            composable(route = Routes.Messages.name) {
                MessagesScreen(
                    onSendMessage = { message ->
                        socketManager.sendMessage(message)
                        viewModel.setMessage(message)
                    },
                    onMessageReceived = { listener -> socketManager.onMessageReceived(listener) },
                    messageList = uiState.messageList,
                    addMessage = { message -> viewModel.addMessage(message) },
                )
            }
        }
    }
}