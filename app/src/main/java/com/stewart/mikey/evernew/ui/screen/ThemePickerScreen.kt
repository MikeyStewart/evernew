package com.stewart.mikey.evernew.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stewart.mikey.evernew.ui.component.PrimaryButton
import com.stewart.mikey.evernew.ui.theme.EvernewTheme

@Composable
fun ThemePickerScreen(
    onThemeClick: (String) -> Unit
) {
    // TODO: Generate theme options with *AI*
    val themeOptions = listOf(
        "Space adventure",
        "Mushroom kingdom uprising",
        "Battle of the capybaras"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pick your adventure",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        themeOptions.forEach { theme ->
            PrimaryButton(
                text = theme,
                modifier = Modifier.fillMaxWidth()
            ) {
                onThemeClick(theme)
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun ThemePickerScreenPreview() {
    EvernewTheme {
        ThemePickerScreen {}
    }
}