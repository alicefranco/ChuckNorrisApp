package br.pprojects.chucknorrisapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.pprojects.chucknorrisapp.data.database.DatabaseRepository
import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.data.model.ResultAPI
import br.pprojects.chucknorrisapp.data.repository.JokesRepository
import br.pprojects.chucknorrisapp.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: JokesRepository, private val databaseRepository: DatabaseRepository) : BaseViewModel() {
    private var joke: MutableLiveData<Joke> = MutableLiveData()

    fun searchJokeByCategory(category: String) {
        viewModelScope.launch {
            loading.value = NetworkState.LOADING
            error.value = ""
            when (val response = repository.getJokeByCategory(category)) {
                is ResultAPI.Success -> {
                    loading.value = NetworkState.DONE
                    joke.value = response.data
                    response.data.categories = checkUncategorized(response.data.categories)
                    databaseRepository.insertJoke(response.data)
                }
                is ResultAPI.SuccessNoBody,
                is ResultAPI.NotFound -> {
                    loading.value = NetworkState.DONE
                    error.value = "There is no such category."
                }
                else -> {
                    loading.value = NetworkState.ERROR
                    error.value = "Unknown error happened, you might need to check your connection and try again."
                }
            }
        }
    }

    fun searchRandomJoke() {
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = NetworkState.LOADING
            error.value = ""
            when (val response = repository.getRandomJoke()) {
                is ResultAPI.Success -> {
                    loading.value = NetworkState.DONE
                    response.data.categories = checkUncategorized(response.data.categories)
                    response.data.largeJoke = checkSize(response.data.value)
                    joke.value = response.data
                    databaseRepository.insertJoke(response.data)
                }
                is ResultAPI.SuccessNoBody -> {
                    loading.value = NetworkState.DONE
                    error.value = "There are no jokes for this category."
                }
                else -> {
                    loading.value = NetworkState.ERROR
                    error.value = "Unknown error happened, you might need to check your connection and try again."
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