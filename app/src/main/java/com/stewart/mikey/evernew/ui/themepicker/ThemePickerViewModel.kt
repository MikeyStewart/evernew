package com.stewart.mikey.evernew.ui.themepicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.stewart.mikey.evernew.domain.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ThemePickerViewModel(
    generativeModel: GenerativeModel
) : ViewModel() {

    private val _uiState: MutableStateFlow<ThemePickerUiState> =
        MutableStateFlow(ThemePickerUiState())
    val uiState: StateFlow<ThemePickerUiState> =
        _uiState.asStateFlow()

    private var chat: Chat = generativeModel.startChat()

    init {
        loadThemes()
    }

    fun loadThemes() {
        _uiState.value = _uiState.value.copy(loading = true)

        viewModelScope.launch {
            try {
                val result = chat.sendMessage(themesPrompt)

                if (result.text.isNullOrBlank())
                    throw NoSuchElementException("Something went wrong generating themes")

                _uiState.value = _uiState.value.copy(
                    themes = mapThemes(result.text!!),
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

    private fun mapThemes(json: String): List<Theme> {
        return Json.decodeFromString<List<Theme>>(json)
    }
}

private const val themesPrompt = """
    Give me 3 theme ideas for an RPG. The themes should include a title and a description.
    Format the response as JSON. Do not include anything outside of the square brackets. 
    For example:
    [
        {
		    title: "theme title",
    	    description: "theme description"
        },
        {
		    title: "another theme title",
    	    description: "another theme description"
        }
    ]
"""