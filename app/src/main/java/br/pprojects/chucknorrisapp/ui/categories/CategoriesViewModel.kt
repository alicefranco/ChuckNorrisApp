package br.pprojects.chucknorrisapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.data.model.ResultAPI
import br.pprojects.chucknorrisapp.data.repository.JokesRepository
import br.pprojects.chucknorrisapp.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CategoriesViewModel(private val repository: JokesRepository) : BaseViewModel(), CoroutineScope {
    private var categories: MutableLiveData<List<String>> = MutableLiveData()
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun searchCategories() {
        launch {
            loading.value = NetworkState.LOADING
            error.value = ""
            val response = repository.getCategories()
            when (response) {
                is ResultAPI.Success -> {
                    loading.value = NetworkState.DONE
                    categories.value = response.data
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
                    //TODO
                }
            }
        }
    }

    fun getCategories(): LiveData<List<String>> {
        return categories
    }
}