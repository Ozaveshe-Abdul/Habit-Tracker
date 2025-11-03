import org.example.habit.data.data_source.HabitDao
import org.example.habit.data.data_source.HabitDatabase
import org.example.habit.data.data_source.getHabitDao
import org.example.habit.data.data_source.getRoomDatabase
import org.example.habit.data.repository.HabitRepositoryImpl
import org.example.habit.domain.repository.HabitRepository
import org.example.habit.presentation.add.AddHabitViewModel
import org.example.habit.presentation.home.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun platformModule(): Module
object KoinModule {


    fun initKoin(config: KoinAppDeclaration? = null) =
        startKoin {
            config?.invoke(this)
            modules(
                // add your module here
                provideViewModelModule, provideRepositoryModule, provideDatabaseModule, platformModule()
            )
        }

//
//    val provideDataSourceModule = module {
//        singleOf(::NoteLocalDataSourceImpl).bind(NoteLocalDataSource::class)
//    }
//

    val provideDatabaseModule = module {
        // 1. Get the final, platform-specific HabitDatabase instance.
        //    Koin will automatically call the platform's getHabitDatabase() function.
        single<HabitDatabase> {
            getRoomDatabase(get())
        }
        single<HabitDao> { getHabitDao(get()) }
//            val platformBuilder = HabitDatabaseConstructor.initialize()
//
//            // Pass the platform-configured builder to your common helper function
//            // to set the driver and CoroutineContext.
//            getRoomDatabase()}
//
//        // 2. Provide the DAO from the database instance
//        single<HabitDao> { get<HabitDatabase>().getDao() }
    }
    val provideRepositoryModule = module {
        singleOf(::HabitRepositoryImpl).bind(HabitRepository::class)
    }
//
//    val provideUseCaseModule = module {
//        singleOf(::CreateNoteUseCase)
//        singleOf(::GetAllNotesUseCase)
//        singleOf(::DeleteNoteUseCase)
//        singleOf(::UpdateNoteUseCase)
//        singleOf(::GetNoteUseCase)
//    }

    val provideViewModelModule = module {
        viewModelOf(::AddHabitViewModel)
        viewModelOf(::HomeViewModel)
    }
}