package com.example.whisper

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WhisperApp() {
    var currentScreen by remember { mutableStateOf("send") }

    Surface {
        when (currentScreen) {
            "send" -> SendMessageScreen(onNavigate = { currentScreen = "view" })
            "view" -> ViewMessagesScreen(onNavigateBack = { currentScreen = "send" })
        }
    }
}

@Preview
@Composable
fun PreviewWhisperApp() {
    WhisperApp()
}
