package org.example.habit.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.habit.data.entities.HabitEntity
import org.example.habit.domain.models.Habit

interface HabitRepository {
    fun getHabits(): Flow<List<HabitEntity>>

    suspend fun insertHabit(habit: HabitEntity)

    suspend fun insertHabit(habit: List<HabitEntity>)

    suspend fun habitCount(): Int

    suspend fun deleteHabit(id: Long)
}