package br.pprojects.chucknorrisapp.di

import br.pprojects.chucknorrisapp.ui.categories.CategoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    viewModel { CategoriesViewModel(get()) }
}