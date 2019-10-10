package br.pprojects.chucknorrisapp.data.network

import br.pprojects.chucknorrisapp.data.model.Joke
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("jokes/random")
    fun getRandomJoke(): Call<Joke>

    @GET("jokes/random")
    fun getJokeByCategory(@Query("category") category: String): Call<Joke>

    @GET("categories")
    fun getCategories(): Call<List<String>>

    companion object {
        fun create(): ApiService {
            return RetrofitManager()
                .build()
                .create(ApiService::class.java)
        }
    }
}