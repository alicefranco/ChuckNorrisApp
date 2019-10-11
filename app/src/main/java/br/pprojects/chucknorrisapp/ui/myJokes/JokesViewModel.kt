package br.pprojects.chucknorrisapp.ui.myJokes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.pprojects.chucknorrisapp.data.database.DatabaseRepository
import br.pprojects.chucknorrisapp.data.model.Joke
import br.pprojects.chucknorrisapp.data.model.NetworkState
import br.pprojects.chucknorrisapp.ui.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class JokesViewModel(private val repository: DatabaseRepository) : BaseViewModel(), CoroutineScope {
    private var myJokes: MutableLiveData<List<Joke>> = MutableLiveData()
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun searchMyJokes() {
        CoroutineScope(Dispatchers.Main).launch {
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