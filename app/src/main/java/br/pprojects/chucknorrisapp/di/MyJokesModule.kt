package br.pprojects.chucknorrisapp.di

import br.pprojects.chucknorrisapp.data.database.DatabaseRepository
import br.pprojects.chucknorrisapp.data.database.DatabaseRepositoryImpl
import br.pprojects.chucknorrisapp.ui.myJokes.JokesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myJokesModule = module {
    viewModel { JokesViewModel(get()) }

    single<DatabaseRepository> {
        DatabaseRepositoryImpl(get())
    }
}