@file:OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)

package org.example.habit.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.habit.domain.models.Habit
import org.example.habit.presentation.add.AddHabitScreen
import org.example.habit.presentation.add.AddHabitViewModel
import org.example.habit.presentation.add.AddScreenEvent
import org.example.habit.presentation.home.components.HabitsList
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
private fun HomeScreen(uiState: HomeUiState, onDeleteClick: (Long) -> Unit, onCheck: (Habit) -> Unit) {
//    var showBottomSheet by remember { mutableStateOf(false) }
    val viewModel = koinViewModel<AddHabitViewModel>()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Track Habits")
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.actions(AddScreenEvent.FloatingButtonClick) }) {
//                Icon(imageVector = )
            }
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            // If this is true, the list won't show
            CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
        } else if (uiState.habits.isEmpty()) {
            // Explicitly handle the empty case
            Text(
                text = "No habits yet. Add one!",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxSize().wrapContentSize()
            )
        } else {

            HabitsList(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
//                .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                habits = uiState.habits,
                onDeleteClick = onDeleteClick,
                onCheck = onCheck
            )
        }
        AddHabitScreen(addHabitViewModel = viewModel)
    }
}

@Composable
fun HomeScreenRoot(){
    val viewmodel = koinViewModel<HomeViewModel>()

    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    HomeScreen(uiState, onDeleteClick = { viewmodel.deleteHabit(it) }, onCheck = {viewmodel.updateDoneState(it)})
//    TornadoExampleScreen()
}