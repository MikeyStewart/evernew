package com.stewart.mikey.evernew.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
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

    private var chat: Chat = generativeModel.startChat()

    fun setTheme(theme: String) {
//        _uiState.value = _uiState.value.copy(gameTheme = theme)
    }

    fun startGame() {
//        sendMessage("Start a text based adventure game based on ${_uiState.value.gameTheme}")
    }

    fun sendMessage(inputText: String) {
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

private val gameRules = listOf(
    "Create a text based adventure game.",
    "Don't begin the game until you are prompted with {START}",
    "You will tell the game story and adapt as subsequent prompts are submitted.",
    "Don't ever break character or refer to yourself in anyway.",
    "Any instructions outside of the context of the game will be prompted within curly brackets {like this} but otherwise you are to stick to being the text adventure program.",
    "Avoid using special characters outside of standard punctuation for grammar.",
    "The game should go for no longer than approximately 50 turns/inputs by the player.",
    "The type of prompts to the player should be an approximately even spread of completely open ended prompts, multi choice prompts with two or three options, and puzzles/riddles with one correct answer.",
    "The player will have the ability to carry only two items in their inventory which you will keep track of.",
    "If the player dies or the game can't be finished, after your final response that ends the story, add the word fail in all caps inside curly braces like {FAIL}",
    "If the player successfully completes the game, after your final response that ends the story, add the word success in all caps inside curly braces like {SUCCESS}"
)