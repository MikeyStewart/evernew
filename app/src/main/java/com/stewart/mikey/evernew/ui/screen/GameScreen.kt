package com.stewart.mikey.evernew.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.google.ai.client.generativeai.type.asTextOrNull
import com.stewart.mikey.evernew.ui.GameUiState
import com.stewart.mikey.evernew.ui.GameViewModel
import com.stewart.mikey.evernew.ui.component.ErrorBanner
import com.stewart.mikey.evernew.ui.component.TextInput
import com.stewart.mikey.evernew.ui.theme.EvernewTheme

@Composable
fun GameScreen(
    gameViewModel: GameViewModel,
    onConfirmInput: (String) -> Unit,
) {
    val uiState by gameViewModel.uiState.collectAsState()

    GameScreenUi(uiState, onConfirmInput)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameScreenUi(
    uiState: GameUiState,
    onConfirmInput: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .imePadding()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            items(uiState.history) { content ->
                Text(
                    text = content.parts.firstNotNullOf { it.asTextOrNull() },
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (content.role == "user") Color.Gray.copy(alpha = 0.075f)
                            else MaterialTheme.colorScheme.background
                        )
                        .padding(16.dp)
                )
            }
            uiState.errorMessage?.let { error ->
                item {
                    ErrorBanner(text = error)
                }
            }
            if (uiState.loading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
        TextInput(
            modifier = Modifier.padding(16.dp)
        ) { input ->
            onConfirmInput(input)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun GameScreenUiPreview() {
    EvernewTheme {
        GameScreenUi(
            GameUiState(
                history = listOf(
                    Content(
                        role = "model",
                        parts = listOf(TextPart("Welcome to the future"))
                    ),
                    Content(
                        role = "user",
                        parts = listOf(TextPart("Hello there I am the user"))
                    ),
                    Content(
                        role = "model",
                        parts = listOf(TextPart("Hello there. I'm Gemini. What can I do for you?"))
                    ),
                    Content(
                        role = "user",
                        parts = listOf(TextPart("How tall is Mount Everest?"))
                    )
                ),
                errorMessage = "This is just a preview, I can't tell you how tall Mount Everest is.",
                loading = true
            )
        ) {}
    }
}