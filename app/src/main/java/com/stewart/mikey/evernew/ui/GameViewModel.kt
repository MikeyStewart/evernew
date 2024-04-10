package com.stewart.mikey.evernew.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    generativeModel: GenerativeModel
) : ViewModel() {

    private val _uiState: MutableStateFlow<GameUiState> =
        MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> =
        _uiState.asStateFlow()

    val chat = generativeModel.startChat(
        history = listOf(
            content(role = "user") { text("Hello, I have 2 dogs in my house.") },
            content(role = "model") { text("Great to meet you. What would you like to know?") }
        )
    )

    fun summarize(inputText: String) {
        _uiState.value = _uiState.value.copy(loading = true)

        viewModelScope.launch {
            try {
                chat.sendMessage(inputText)
                _uiState.value = _uiState.value.copy(
                    history = chat.history,
                    loading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Something went wrong"
                )
            }
        }
    }
}