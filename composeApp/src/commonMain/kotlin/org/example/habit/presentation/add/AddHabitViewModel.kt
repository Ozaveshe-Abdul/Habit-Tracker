package org.example.habit.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.habit.data.entities.toHabitEntity
import org.example.habit.domain.models.Habit
import org.example.habit.domain.repository.HabitRepository

class AddHabitViewModel(private val repository: HabitRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState = _uiState.asStateFlow().onStart {

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500L), AddUiState())

    fun actions (event: AddScreenEvent) {
        when(event) {

            AddScreenEvent.SaveButtonClick -> {
                addHabit()
            }
            is AddScreenEvent.TextFieldInputChange -> {
                _uiState.update { it.copy(habitName = event.newString) }
            }

            AddScreenEvent.FloatingButtonClick -> {
                _uiState.update { it.copy(isShowBottomSheet = true) }
            }

            AddScreenEvent.DismissBottomSheet -> {
                _uiState.update { it.copy(isShowBottomSheet = false, habitName = "") }
            }
        }
    }

   private fun addHabit(){
       _uiState.value.habitName.takeIf{ !it.isEmpty() }
           ?.let { habitName ->
               viewModelScope.launch {

                   repository.insertHabit(
                       Habit(
                           id = 0,
                           name = habitName,
                           streakCount = 0,
                           highestStreak = 0,
                           isDone = false
                       ).toHabitEntity()
                   )

                   _uiState.update { it.copy(isShowBottomSheet = false, habitName = "") }
               }
           }
    }
}

data class AddUiState (
    val habitName: String = "",
    val isShowBottomSheet: Boolean = false
)
