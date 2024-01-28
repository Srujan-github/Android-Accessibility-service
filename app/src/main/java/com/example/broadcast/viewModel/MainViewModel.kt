package com.example.broadcast.viewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewMode : ViewModel(){
    private var _idletimer=MutableLiveData(0)
    val idletimer : MutableLiveData<Int> get() =_idletimer

    private val handler = Handler(Looper.getMainLooper())
    private var isIdleRunning=false
    private val runnable = object : Runnable {
        override fun run() {
            // Increment timer value
            _idletimer.postValue(_idletimer.value?.plus(1))
            // Schedule the runnable again after 1 second
            handler.postDelayed(this, 1000)
        }
    }
    fun startIdleTimer() {
     handler.post(runnable)
        isIdleRunning=true
    }
    fun isIdleRunning():Boolean{
        return isIdleRunning
    }

    // Stop the timer
    fun stopIdleTimer() {
        // Remove the runnable callback to stop the timer
        handler.removeCallbacks(runnable)
        _idletimer.value =0
        isIdleRunning=false
    }

}