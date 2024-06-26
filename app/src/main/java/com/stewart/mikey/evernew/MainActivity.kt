package com.stewart.mikey.evernew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.ai.client.generativeai.GenerativeModel
import com.stewart.mikey.evernew.ui.game.GameViewModel
import com.stewart.mikey.evernew.ui.game.GameScreen
import com.stewart.mikey.evernew.ui.home.HomeScreen
import com.stewart.mikey.evernew.ui.themepicker.ThemePickerScreen
import com.stewart.mikey.evernew.ui.theme.EvernewTheme
import com.stewart.mikey.evernew.ui.themepicker.ThemePickerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: DI (future improvement)
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.apiKey
        )
        val themePickerViewModel = ThemePickerViewModel(generativeModel)
        val gameViewModel = GameViewModel(generativeModel)

        setContent {
            EvernewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    // Simple navigation
                    var navigationDestinationState: NavigationDestination by rememberSaveable {
                        mutableStateOf(NavigationDestination.Home)
                    }
                    AnimatedContent(
                        targetState = navigationDestinationState,
                        label = "navigation"
                    ) { navigationDestination ->
                        when (navigationDestination) {
                            NavigationDestination.Home -> {
                                HomeScreen {
                                    navigationDestinationState = NavigationDestination.ThemePicker
                                }
                            }

                            NavigationDestination.ThemePicker -> {
                                ThemePickerScreen(themePickerViewModel) { theme ->
                                    gameViewModel.sendMessage("Create the introduction to a text based RPG based on ${theme.title}")
                                    navigationDestinationState = NavigationDestination.Game
                                }
                            }

                            NavigationDestination.Game -> {
                                GameScreen(gameViewModel) {
                                    // TODO: handle input (move code into Game screen?)
                                }
                            }
                        }
                    }
                    BackHandler {
                        navigationDestinationState = NavigationDestination.Home
                    }
                }
            }
        }
    }
}

enum class NavigationDestination {
    Home, ThemePicker, Game
}

private val startingPrompt =
    "{START} Write in introduction to the game including some main characters and locations based on the theme of: "