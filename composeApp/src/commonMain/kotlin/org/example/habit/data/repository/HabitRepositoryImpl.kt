package org.example.habit.data.repository

import kotlinx.coroutines.flow.Flow
import org.example.habit.data.data_source.HabitDao
import org.example.habit.data.entities.HabitEntity
import org.example.habit.domain.repository.HabitRepository

class HabitRepositoryImpl(private val dao: HabitDao) : HabitRepository {
    override fun getHabits(): Flow<List<HabitEntity>> = dao.getAllHabitAsFlow()

    override suspend fun insertHabit(habit: HabitEntity) = dao.insertHabit(habit)

    override suspend fun insertHabit(habit: List<HabitEntity>) = dao.insertHabit(habit)

    override suspend fun habitCount() = dao.countHabit()

    override suspend fun deleteHabit(id: Long) = dao.deleteHabit(id)
}