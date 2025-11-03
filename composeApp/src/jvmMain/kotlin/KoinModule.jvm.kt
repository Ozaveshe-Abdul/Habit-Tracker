import androidx.room.RoomDatabase
import org.example.habit.data.data_source.HabitDatabase
import org.example.habit.getDatabaseBuilder
import org.koin.dsl.module

actual fun platformModule() = module {
    single<RoomDatabase.Builder<HabitDatabase>> { getDatabaseBuilder() }
}