package org.example.habit.domain.models

data class Habit(
    val name: String,
    val streakCount: Int,
    val highestStreak: Int,
    var isDone: Boolean
)
