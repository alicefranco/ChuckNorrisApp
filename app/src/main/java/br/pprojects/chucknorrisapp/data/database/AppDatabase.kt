package br.pprojects.chucknorrisapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.util.DatabaseTypeConverter

@TypeConverters(DatabaseTypeConverter::class)
@Database(entities = arrayOf(Joke::class), version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokesDao
}