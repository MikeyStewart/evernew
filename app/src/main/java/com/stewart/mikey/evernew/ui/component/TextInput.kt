package com.stewart.mikey.evernew.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stewart.mikey.evernew.ui.theme.EvernewTheme

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    onConfirm: (String) -> Unit,
) {
    var input by rememberSaveable { mutableStateOf("") }

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
            leadingIcon = { Text(text = "〉") },
            shape = RectangleShape
        )

        OutlinedButton(
            onClick = {
                if (input.isNotBlank()) {
                    onConfirm(input)
                }
            },
            enabled = input.isNotBlank(),
            shape = RectangleShape,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text("Enter")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun TextInputPreview() {
    EvernewTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            TextInput {}
        }
    }
}