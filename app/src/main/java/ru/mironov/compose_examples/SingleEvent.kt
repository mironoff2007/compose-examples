package ru.mironov.compose_examples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData

class SingleEvent<T> {

    val mutableLiveData = MutableLiveData<T>(null)

    var state: T? = null

    fun onEvent(onEvent: (T) -> Unit) {
        val currentState = state
        mutableLiveData.value = null
        currentState?.let { onEvent.invoke(currentState) }
    }

    fun postEvent(event: T) {
        mutableLiveData.postValue(event)
    }

    @Composable
    inline fun Observe() {
        val currentState by mutableLiveData.observeAsState()
        this.state = currentState
    }

}