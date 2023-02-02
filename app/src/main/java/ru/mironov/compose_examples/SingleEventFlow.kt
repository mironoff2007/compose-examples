package ru.mironov.compose_examples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import kotlinx.coroutines.flow.MutableStateFlow

class SingleEventFlow<T> {

    val flow = MutableStateFlow<T?>(null)

    var state: T? = null

    fun onEvent(onEvent: (T) -> Unit) {
        val currentState = state
        flow.value = null
        currentState?.let { onEvent.invoke(currentState) }
    }

    suspend fun postEventSuspend(event: T) {
        flow.emit(event)
    }

    fun postEvent(event: T) {
        flow.tryEmit(event)
    }

    @Composable
    inline fun Observe() {
        val currentState by flow.collectAsState()
        this.state = currentState
    }

}