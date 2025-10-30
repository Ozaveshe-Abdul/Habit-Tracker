package org.example.habit.presentation.home

import org.example.habit.domain.models.Habit

data class HomeUiState(
    val habits: List<Habit> = emptyList(),
    val isLoading: Boolean = false
)
