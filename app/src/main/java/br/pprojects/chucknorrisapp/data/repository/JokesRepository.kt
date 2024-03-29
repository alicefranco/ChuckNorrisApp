package br.pprojects.chucknorrisapp.data.repository

import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.data.model.ResultAPI
import br.pprojects.chucknorrisapp.data.network.ApiService
import br.pprojects.chucknorrisapp.util.result
import br.pprojects.chucknorrisapp.util.safeCall

interface JokesRepository {
    suspend fun getRandomJoke(): ResultAPI<Joke>
    suspend fun getJokeByCategory(category: String): ResultAPI<Joke>
    suspend fun getCategories(): ResultAPI<List<String>>
}

class JokesRepositoryImpl(private val apiService: ApiService) : JokesRepository {

    override suspend fun getRandomJoke(): ResultAPI<Joke> {
        val response = safeCall { apiService.getRandomJoke() }
        return response.result()
    }

    override suspend fun getJokeByCategory(category: String): ResultAPI<Joke> {
        val response = safeCall { apiService.getJokeByCategory(category) }

        return response.result()
    }

    override suspend fun getCategories(): ResultAPI<List<String>> {
        val response = safeCall { apiService.getCategories() }
        return response.result()
    }
}