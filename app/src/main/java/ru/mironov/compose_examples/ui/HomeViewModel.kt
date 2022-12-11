package ru.mironov.compose_examples.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mironov.compose_examples.SingleEvent
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    sealed class Event() {
        object ButtonEvent: Event()
    }

    val event = SingleEvent<Event>()

    val flag = MutableLiveData(false)

    fun toggle() {
        flag.postValue(!flag.value!!)
    }

    fun toggleAfterDelay(delayValue: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(delayValue)
            toggle()
        }
    }

    fun log(msg: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Home_screen", msg)
        }
    }

}