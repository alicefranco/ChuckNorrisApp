package br.pprojects.chucknorrisapp.data.database

import br.pprojects.chucknorrisapp.data.model.Joke

interface DatabaseRepository  {
    suspend fun insertJoke(joke: Joke)
    suspend fun getMyJokes(): List<Joke>
}

class DatabaseRepositoryImpl(private val jokesDao: JokesDao): DatabaseRepository {
    override suspend fun insertJoke(joke: Joke) {
       jokesDao.insert(joke)
    }

    override suspend fun getMyJokes(): List<Joke> {
        return jokesDao.getAll()
    }
}
