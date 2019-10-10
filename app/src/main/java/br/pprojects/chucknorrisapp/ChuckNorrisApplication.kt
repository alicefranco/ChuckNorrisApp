package br.pprojects.chucknorrisapp

import android.app.Application
import br.pprojects.chucknorrisapp.di.jokeModule
import br.pprojects.chucknorrisapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChuckNorrisApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ChuckNorrisApplication)
            modules(
                listOf(
                    networkModule,
                    jokeModule
                )
            )
        }
    }
}