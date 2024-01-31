package br.pprojects.chucknorrisapp.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.data.model.ResultAPI
import br.pprojects.chucknorrisapp.data.repository.JokesRepository
import br.pprojects.chucknorrisapp.ui.BaseViewModel
import kotlinx.coroutines.launch

class CategoriesViewModel(private val repository: JokesRepository) : BaseViewModel() {
    private var categories: MutableLiveData<List<String>> = MutableLiveData()

    fun searchCategories() {
        viewModelScope.launch {
            loading.value = NetworkState.LOADING
            error.value = ""
            when (val response = repository.getCategories()) {
                is ResultAPI.Success -> {
                    loading.value = NetworkState.DONE
                    categories.value = response.data
                }
                is ResultAPI.SuccessNoBody,
                is ResultAPI.NotFound -> {
                    loading.value = NetworkState.DONE
                    error.value = "There are no categories."
                }
                else -> {
                    loading.value = NetworkState.ERROR
                    error.value = "Unknown error happened, you might need to check your connection and try again."
                }
            }
        }
    }

    fun getCategories(): LiveData<List<String>> {
        return categories
    }
}