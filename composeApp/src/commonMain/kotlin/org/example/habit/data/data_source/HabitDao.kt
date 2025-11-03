package org.example.habit.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.example.habit.data.entities.HabitEntity

@Dao
interface HabitDao {
    @Upsert
    suspend fun insertHabit(item: HabitEntity)

    @Upsert
    suspend fun insertHabit(items: List<HabitEntity>)

    @Query("DELETE FROM habit_table WHERE id = :id")
    suspend fun deleteHabit(id: Long)

    @Query("SELECT count(*) FROM habit_table")
    suspend fun countHabit(): Int

    @Query("SELECT * FROM habit_table")
    fun getAllHabitAsFlow(): Flow<List<HabitEntity>>
}