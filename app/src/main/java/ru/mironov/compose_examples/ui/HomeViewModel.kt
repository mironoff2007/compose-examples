package ru.mironov.compose_examples.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mironov.compose_examples.SingleEventFlow
import ru.mironov.compose_examples.SingleEventLiveData
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    sealed class Event() {
        object ButtonEvent: Event()
    }

    val event = SingleEventLiveData<Event>()
    val flowEvent = SingleEventFlow<Event>()

    private val flag = MutableLiveData(false)
    private val flagFlow = MutableLiveData(false)

    private fun toggleFlag() {
        flag.postValue(!flag.value!!)
    }

    private fun toggleFlagFlow() {
        flagFlow.postValue(!flagFlow.value!!)
    }

    fun toggleAfterDelay(delayValue: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(delayValue)
            toggleFlag()
        }
    }

    fun log(msg: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Home_screen", msg)
        }
    }

    fun postFlowEvent(event: Event.ButtonEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            flowEvent.postEventSuspend(event)
        }
    }

}