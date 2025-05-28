package com.twinstech.aiassistant

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AIAssistantUiState(
    val isFirstTime: Boolean = true,
    val userName: String = "",
    val chatHistory: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class AIAssistantViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AIAssistantUiState())
    val uiState: StateFlow<AIAssistantUiState> = _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "YOUR_ACTUAL_GEMINI_API_KEY_HERE" // Replace with your actual API key
    )

    companion object {
        private const val PREF_NAME = "ai_assistant_prefs"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_IS_FIRST_TIME = "is_first_time"
    }

    fun initializeApp(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val isFirstTime = prefs.getBoolean(KEY_IS_FIRST_TIME, true)
        val userName = prefs.getString(KEY_USER_NAME, "") ?: ""

        _uiState.value = _uiState.value.copy(
            isFirstTime = isFirstTime,
            userName = userName
        )
    }

    fun saveUserName(context: Context, name: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(KEY_USER_NAME, name)
            .putBoolean(KEY_IS_FIRST_TIME, false)
            .apply()

        _uiState.value = _uiState.value.copy(
            isFirstTime = false,
            userName = name,
            chatHistory = listOf(
                ChatMessage(
                    content = "Hello $name! I'm your AI assistant. How can I help you today?",
                    isFromUser = false
                )
            )
        )
    }

    fun sendMessage(message: String) {
        val currentState = _uiState.value
        val userMessage = ChatMessage(content = message, isFromUser = true)

        _uiState.value = currentState.copy(
            chatHistory = currentState.chatHistory + userMessage,
            isLoading = true,
            error = null
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val prompt = if (currentState.userName.isNotEmpty()) {
                    "User name: ${currentState.userName}. User message: $message"
                } else {
                    message
                }

                val response = generativeModel.generateContent(prompt)
                val aiResponse = response.text ?: "I apologize, but I couldn't generate a response. Please try again."

                val aiMessage = ChatMessage(content = aiResponse, isFromUser = false)

                _uiState.value = _uiState.value.copy(
                    chatHistory = _uiState.value.chatHistory + aiMessage,
                    isLoading = false
                )
            } catch (e: Exception) {
                val errorMessage = ChatMessage(
                    content = "I'm sorry, but I encountered an error: ${e.localizedMessage ?: "Unknown error"}. Please try again.",
                    isFromUser = false
                )

                _uiState.value = _uiState.value.copy(
                    chatHistory = _uiState.value.chatHistory + errorMessage,
                    isLoading = false,
                    error = e.localizedMessage
                )
            }
        }
    }

    fun copyMessageToClipboard(message: String) {
        // This will be called from the UI with context
    }
}