package br.pprojects.chucknorrisapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.data.model.ResultAPI
import br.pprojects.chucknorrisapp.data.repository.JokesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JokesViewModel(private val repository: JokesRepository) : ViewModel() {
    var loading: MutableLiveData<NetworkState> = MutableLiveData()
    var joke: MutableLiveData<Joke> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()

    fun getJokeByCategory(category: String) {
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = NetworkState.LOADING
            error.value = ""
            val response = repository.getJokeByCategory(category)
            when (response) {
                is ResultAPI.Success -> {
                    loading.value = NetworkState.DONE
                    joke.value = response.data
                }
                is ResultAPI.NotFound -> {
                    loading.value = NetworkState.ERROR
                    error.value = "There are no jokes for this category."
                }
                is ResultAPI.InternalError -> {
                    loading.value = NetworkState.NO_CONNECTION
                    error.value = "Please, check your internet connection and try again."
                }
            }
        }
    }
}