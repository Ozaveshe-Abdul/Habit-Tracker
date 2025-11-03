package org.example.habit

import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.habit.data.data_source.HabitDatabase
import java.io.File

// shared/src/jvmMain/kotlin/Database.desktop.kt

fun getDatabaseBuilder(): RoomDatabase.Builder<HabitDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "my_room.db")
    return Room.databaseBuilder<HabitDatabase>(
        name = dbFile.absolutePath,
    )
}


// And the generated object is configured to use it:
//actual object HabitDatabaseConstructor : RoomDatabaseConstructor<HabitDatabase> {
//    override fun initialize(): HabitDatabase {
//        // This generated code will use YOUR manually defined getDatabaseBuilder()
//        return getDatabaseBuilder().build()
//    }
//}
