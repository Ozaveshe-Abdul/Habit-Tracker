package org.example.habit

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Habit Tracker",
    ) {
        KoinModule.initKoin()
        App()
    }
}