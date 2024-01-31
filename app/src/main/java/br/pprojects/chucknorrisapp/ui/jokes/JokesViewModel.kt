package br.pprojects.chucknorrisapp.ui.jokes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.pprojects.chucknorrisapp.data.database.DatabaseRepository
import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.ui.BaseViewModel
import kotlinx.coroutines.launch

class JokesViewModel(private val repository: DatabaseRepository) : BaseViewModel() {
    private var myJokes: MutableLiveData<List<Joke>> = MutableLiveData()

    fun searchMyJokes() {
        viewModelScope.launch {
            loading.value = NetworkState.LOADING
            error.value = ""
            myJokes.value = repository.getMyJokes()
            loading.value = NetworkState.DONE
        }
    }

    fun getMyJokes(): LiveData<List<Joke>> {
        return myJokes
    }
}