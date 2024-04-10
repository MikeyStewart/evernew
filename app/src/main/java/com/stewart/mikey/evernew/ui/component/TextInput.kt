package com.stewart.mikey.evernew.ui.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stewart.mikey.evernew.ui.theme.EvernewTheme

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    loading: Boolean,
    onConfirm: (String) -> Unit,
) {
    var input by rememberSaveable { mutableStateOf("") }
    val density = LocalDensity.current
    var buttonWidthPx by remember {
        mutableStateOf<Int?>(null)
    }
    val buttonWidth by remember {
        derivedStateOf {
            with(density) {
                buttonWidthPx?.toDp() ?: 24.dp
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = input,
            singleLine = true,
            placeholder = { Text("Input") },
            onValueChange = { input = it },
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            shape = RectangleShape
        )

        AnimatedContent(targetState = loading) { isLoading ->
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .width(buttonWidth)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                OutlinedButton(
                    onClick = {
                        if (input.isNotBlank()) {
                            onConfirm(input)
                            input = ""
                        }
                    },
                    enabled = input.isNotBlank(),
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxHeight()
                        .onGloballyPositioned {
                            if (buttonWidthPx == null) {
                                buttonWidthPx = it.size.width
                            }
                        }
                ) {
                    Text("Enter")
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun TextInputPreview() {
    EvernewTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            TextInput(loading = false) {}
        }
    }
}