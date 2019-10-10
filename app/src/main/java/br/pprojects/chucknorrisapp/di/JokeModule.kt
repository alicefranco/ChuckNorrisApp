package br.pprojects.chucknorrisapp.di

import br.pprojects.chucknorrisapp.data.repository.JokesRepository
import br.pprojects.chucknorrisapp.data.repository.JokesRepositoryImpl
import br.pprojects.chucknorrisapp.ui.JokesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val jokeModule = module {
    viewModel { JokesViewModel(get()) }
    single<JokesRepository> { JokesRepositoryImpl(get()) }
}