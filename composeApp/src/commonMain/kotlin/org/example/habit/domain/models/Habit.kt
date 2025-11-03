package org.example.habit.domain.models

data class Habit(
    val id: Long,
    val name: String,
    val streakCount: Int,
    val highestStreak: Int,
    var isDone: Boolean
)
