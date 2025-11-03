package org.example.habit

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.habit.data.data_source.HabitDatabase
import org.koin.dsl.module

// shared/src/androidMain/kotlin/Database.android.kt

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<HabitDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("my_room.db")
    return Room.databaseBuilder<HabitDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}


