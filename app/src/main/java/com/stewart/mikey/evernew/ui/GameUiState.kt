package com.stewart.mikey.evernew.ui

import com.google.ai.client.generativeai.type.Content

data class GameUiState(
    val history: List<Content> = emptyList(),
    val loading: Boolean = false,
    val errorMessage: String? = null,
)