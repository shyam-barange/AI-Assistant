package com.twinstech.aiassistant

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AIAssistantApp(
    viewModel: AIAssistantViewModel = viewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

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

        else -> {
            ChatScreen(viewModel = viewModel)
        }
    }
}