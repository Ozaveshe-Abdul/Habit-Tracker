package org.example.habit.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.example.habit.data.entities.HabitEntity
import org.example.habit.data.entities.toHabit
import org.example.habit.data.entities.toHabitEntity
import org.example.habit.domain.models.Habit
import org.example.habit.domain.repository.HabitRepository
import kotlin.math.max
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class HomeViewModel(private val repository: HabitRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()/*.onStart { getHabits() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = HomeUiState()
    )*/

    init{
        updateHabitsForNewDay()
        getHabits()
    }

    var getHabitsJob: Job? = null

    fun deleteHabit(id: Long) {
        viewModelScope.launch {
            repository.deleteHabit(id)
        }
    }

    fun updateDoneState(habit: Habit) {
        viewModelScope.launch {
//            val increaseStreak = !habit.isDone
            val modifiedHabit = habit.toHabitEntity().increaseStreak()
            repository.insertHabit(modifiedHabit)

        }
    }

    private fun updateDailyHabit() {
        viewModelScope.launch {
            val previousState = repository.getHabits().first()

            // 'map' now creates a NEW list of NEW HabitEntity objects
            val currentState = previousState.map { habitEntity ->
                habitEntity.increaseStreak()
            }

            // Insert the new, updated list into the repository
            repository.insertHabit(currentState)
        }
    }


    /**
     * Checks all habits and applies streak logic for any that
     * haven't been processed today.
     */
    @OptIn(ExperimentalTime::class)
    private fun updateHabitsForNewDay() {
        viewModelScope.launch {
            val habits = repository.getHabits().first()
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault()).toEpochDays()

            // Use 'map' to create a new list of processed habits
            val processedHabits = habits.map { habit ->
                // Check if this habit has already been processed today
                if (habit.lastUpdated == today) {
                    habit // No changes needed, skip it
                } else {
                    // It's a new day, process the rollover logic
                    processRollover(habit, today)
                }
            }

            // Use your bulk @Upsert function to save the changes
            repository.insertHabit(processedHabits)
        }
    }

    /**
     * This is your new logic. It checks a habit from a previous day
     * and returns an updated copy for the new day.
     */
    private fun processRollover(habit: HabitEntity, todayEpochDay: Long): HabitEntity {

        val (newStreak, newHighestStreak) = if (habit.isDone) {
            // ✅ The user completed it yesterday, so increase streak
            val increasedStreak = habit.streakCount + 1
            Pair(increasedStreak, max(habit.highestStreak, increasedStreak))
        } else {
            // ❌ The user missed yesterday, so reset streak
            Pair(0, habit.highestStreak)
        }

        // Return a new copy of the habit, reset for today
        return habit.copy(
            isDone = false, // Reset 'isDone' for the new day
            streakCount = newStreak,
            highestStreak = newHighestStreak,
            lastUpdated = todayEpochDay // Mark it as processed for today
        )
    }
    private fun getHabits() {
        // Cancel any existing job to avoid duplicate collectors
        getHabitsJob?.cancel()

        // Launch a new job
        getHabitsJob = repository.getHabits()
            .onStart {
                // This runs *before* collection starts
                _uiState.update { it.copy(isLoading = true) }
            }
            .onEach { habitList ->
                println(habitList.firstOrNull())
                // This runs *every time* the database emits a new list.
                // 'habitList' (the 'it' variable) is the List<HabitEntity>
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        habits = habitList.map { habitEntity -> habitEntity.toHabit() }
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}