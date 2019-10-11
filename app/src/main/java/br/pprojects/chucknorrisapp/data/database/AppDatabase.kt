package br.pprojects.chucknorrisapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.utils.DatabaseTypeConverter

@TypeConverters(DatabaseTypeConverter::class)
@Database(entities = arrayOf(Joke::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokesDao
}