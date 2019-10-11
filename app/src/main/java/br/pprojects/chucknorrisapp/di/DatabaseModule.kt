package br.pprojects.chucknorrisapp.di

import androidx.room.Room
import br.pprojects.chucknorrisapp.data.database.AppDatabase
import br.pprojects.chucknorrisapp.data.database.DatabaseRepository
import br.pprojects.chucknorrisapp.data.database.DatabaseRepositoryImpl
import br.pprojects.chucknorrisapp.util.Constants
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single { get<AppDatabase>().jokeDao() }
}