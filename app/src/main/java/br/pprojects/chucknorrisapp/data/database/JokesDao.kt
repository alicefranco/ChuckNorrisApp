package br.pprojects.chucknorrisapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.pprojects.chucknorrisapp.data.model.Joke

@Dao
interface JokesDao {

    @Query("SELECT * FROM joke")
    fun getAll(): List<Joke>

    @Insert
    fun insert(joke: Joke)

    @Delete
    fun delete(joke: Joke)
}