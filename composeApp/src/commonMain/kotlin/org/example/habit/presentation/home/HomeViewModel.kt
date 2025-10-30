package org.example.habit.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.habit.presentation.home.components.habits

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()/*.onStart { getHabits() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = HomeUiState()
    )*/

    init{
        getHabits()
    }

    var getHabitsJob: Job? = null

    fun getHabits() {
        getHabitsJob?.cancel()
        getHabitsJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // retrieve from database

            // update ui state
            _uiState.update {
                it.copy(isLoading = false, habits = habits)
            }
        }
    }
}