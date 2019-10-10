package br.pprojects.chucknorrisapp.di

import br.pprojects.chucknorrisapp.data.network.ApiService
import br.pprojects.chucknorrisapp.data.network.RetrofitManager
import org.koin.dsl.module

val networkModule = module {
    single { RetrofitManager() }
    single { ApiService.create() }
}