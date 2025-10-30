package org.example.habit.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.habit.presentation.home.components.HabitsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(uiState: HomeUiState) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text("Track Habits")
                },
            )
        },
    ) { innerPadding ->
        HabitsList(modifier = Modifier.padding(innerPadding).background(MaterialTheme.colorScheme.tertiaryContainer), habits = uiState.habits)
    }
}

@Composable
fun HomeScreenRoot(){
    val viewmodel = HomeViewModel()

    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    HomeScreen(uiState)
}