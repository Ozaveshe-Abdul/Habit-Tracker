package org.example.habit.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlin.math.max
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity(tableName = "habit_table")
data class HabitEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val streakCount: Int,
    val highestStreak: Int,
    val isDone: Boolean,
    val lastUpdated: Long = Clock.System.todayIn(TimeZone.currentSystemDefault()).toEpochDays()) {
    /**
    * This is now an immutable function.
    * It returns a NEW, updated copy of the habit.
    */
    fun increaseStreak(): HabitEntity {
        // If not done, just return the original object. No change.
        if (!isDone) return this

        val newStreak = streakCount + 1
        return this.copy(
            streakCount = newStreak,
            highestStreak = max(highestStreak, newStreak) // Update highestStreak
        )
    }
}

