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
import com.stewart.mikey.evernew.ui.GameViewModel
import com.stewart.mikey.evernew.ui.screen.GameScreen
import com.stewart.mikey.evernew.ui.screen.HomeScreen
import com.stewart.mikey.evernew.ui.screen.ThemePickerScreen
import com.stewart.mikey.evernew.ui.theme.EvernewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EvernewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val generativeModel = GenerativeModel(
                        modelName = "gemini-pro",
                        apiKey = BuildConfig.apiKey
                    )
                    val viewModel = GameViewModel(generativeModel)

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
                                ThemePickerScreen { selectedTheme ->
                                    // TODO: store/use selected theme
                                    navigationDestinationState = NavigationDestination.Game
                                }
                            }

                            NavigationDestination.Game -> {
                                GameScreen(viewModel) { input ->
                                    viewModel.sendMessage(input)
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