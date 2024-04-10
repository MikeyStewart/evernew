package com.stewart.mikey.evernew.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stewart.mikey.evernew.ui.theme.EvernewTheme

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RectangleShape,
        modifier = modifier.defaultMinSize(minHeight = minHeight),
    ) {
        Text(text = text)
    }
}

private val minHeight = 56.dp

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    EvernewTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            PrimaryButton(text = "Click me!") {}
        }
    }
}