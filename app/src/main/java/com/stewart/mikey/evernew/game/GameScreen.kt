package com.stewart.mikey.evernew.game

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stewart.mikey.evernew.ui.component.TextInput
import com.stewart.mikey.evernew.ui.theme.EvernewTheme

@Composable
fun GameScreen(
//    gameViewModel: GameViewModel = viewModel()
) {
//    val gameViewState by gameViewModel.uiState.collectAsState()

    GameScreenUi() {}
}

@Composable
fun GameScreenUi(
//    gameViewState: GameViewState,
    onConfirmInput: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
//                .fadingBottomEdge()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // TODO: Add text history
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
        GameScreenUi() {}
    }
}