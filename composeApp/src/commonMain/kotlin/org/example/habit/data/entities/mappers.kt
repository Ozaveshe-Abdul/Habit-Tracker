package org.example.habit.data.entities

import org.example.habit.domain.models.Habit

fun Habit.toHabitEntity(): HabitEntity {
    return HabitEntity(
        id = this.id,
        name = this.name,
        streakCount = this.streakCount,
        highestStreak = this.highestStreak,
        isDone = this.isDone
    )
}

fun HabitEntity.toHabit(): Habit = Habit(
        id = this.id,
        name = this.name,
        streakCount = this.streakCount,
        highestStreak = this.highestStreak,
        isDone = this.isDone
    )


