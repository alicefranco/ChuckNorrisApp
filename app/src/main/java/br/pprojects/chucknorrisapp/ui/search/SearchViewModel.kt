package br.pprojects.chucknorrisapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.pprojects.chucknorrisapp.data.database.DatabaseRepository
import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.data.model.ResultAPI
import br.pprojects.chucknorrisapp.data.repository.JokesRepository
import br.pprojects.chucknorrisapp.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SearchViewModel(private val repository: JokesRepository, private val databaseRepository: DatabaseRepository) : BaseViewModel(), CoroutineScope {
    private var joke: MutableLiveData<Joke> = MutableLiveData()
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun searchJokeByCategory(category: String) {
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = NetworkState.LOADING
            error.value = ""
            val response = repository.getJokeByCategory(category)
            when (response) {
                is ResultAPI.Success -> {
                    loading.value = NetworkState.DONE
                    joke.value = response.data
                    response.data.categories = checkUncategorized(response.data.categories)
                    databaseRepository.insertJoke(response.data)
                }
                is ResultAPI.NotFound -> {
                    loading.value = NetworkState.ERROR
                    error.value = "There are no jokes for this category."
                }
                is ResultAPI.InternalError -> {
                    loading.value = NetworkState.NO_CONNECTION
                    error.value = "Please, check your internet connection and try again."
                }
                else -> {
                    // TODO
                }
            }
        }
    }

    fun searchRandomJoke() {
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = NetworkState.LOADING
            error.value = ""
            val response = repository.getRandomJoke()
            when (response) {
                is ResultAPI.Success -> {
                    loading.value = NetworkState.DONE
                    response.data.categories = checkUncategorized(response.data.categories)
                    response.data.largeJoke = checkSize(response.data.value)
                    joke.value = response.data
                    databaseRepository.insertJoke(response.data)
                }
                is ResultAPI.NotFound -> {
                    loading.value = NetworkState.ERROR
                    error.value = "There are no jokes for this category."
                }
                is ResultAPI.InternalError -> {
                    loading.value = NetworkState.NO_CONNECTION
                    error.value = "Please, check your internet connection and try again."
                }
                else -> {
                    // TODO
                }
            }
        }
    }

    fun getJoke(): LiveData<Joke> {
        return joke
    }

    private fun checkUncategorized(categories: List<String>): List<String> {
        return categories.ifEmpty { listOf("uncategorized") }
    }

    private fun checkSize(value: String): Boolean {
        return value.length > 80
    }
}