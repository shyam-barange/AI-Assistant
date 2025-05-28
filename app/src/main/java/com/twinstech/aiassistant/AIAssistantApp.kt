// Updated AIAssistantApp.kt with theme toggle and history clearing
package com.twinstech.aiassistant

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AIAssistantApp(
    viewModel: AIAssistantViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var showSettings by remember { mutableStateOf(false) }
    var darkTheme by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.initializeApp(context)
    }

    when {
        uiState.isFirstTime -> {
            OnboardingScreen(
                onNameSubmitted = { name ->
                    viewModel.saveUserName(context, name)
                }
            )
        }

        showSettings -> {
            SettingsScreen(
                userName = uiState.userName,
                darkTheme = darkTheme,
                onToggleTheme = { darkTheme = !darkTheme },
                onClearHistory = {
                    viewModel.clearChatHistory(context)
                },
                onBack = {
                    showSettings = false
                }
            )
        }

        else -> {
            ChatScreen(
                viewModel = viewModel,
                onSettingsClick = { showSettings = true }
            )
        }
    }
}