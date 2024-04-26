package com.stewart.mikey.evernew.ui.themepicker

import com.stewart.mikey.evernew.domain.Theme

data class ThemePickerUiState(
    val themes: List<Theme> = emptyList(),
    val loading: Boolean = false,
    val errorMessage: String? = null,
)