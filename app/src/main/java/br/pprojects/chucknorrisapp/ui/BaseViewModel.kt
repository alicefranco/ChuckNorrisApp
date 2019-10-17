package br.pprojects.chucknorrisapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.pprojects.chucknorrisapp.data.model.NetworkState

open class BaseViewModel : ViewModel() {
    protected var loading: MutableLiveData<NetworkState> = MutableLiveData()
    protected var error: MutableLiveData<String> = MutableLiveData()

    fun getLoading(): LiveData<NetworkState> {
        return loading
    }

    fun getError(): LiveData<String> {
        return error
    }
}