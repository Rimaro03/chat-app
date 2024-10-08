package com.example.chat_app

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: Routes,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.name) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = ("back")
                    )
                }
            }
        }
    )
}

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

    Scaffold (
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.popBackStack() }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val messages by viewModel.messages.collectAsState()

        NavHost(
            navController = navController,
            startDestination = Routes.Login.name,
            modifier = Modifier.padding(innerPadding)
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
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}