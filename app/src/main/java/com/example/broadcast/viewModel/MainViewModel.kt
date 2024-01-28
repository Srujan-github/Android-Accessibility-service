package com.example.broadcast.viewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewMode : ViewModel(){
    private var _idletimer=MutableLiveData(0)
    val idletimer : LiveData<Int> get() =_idletimer
 private var _dialogTimer=MutableLiveData(8)
    val dialogTimer : LiveData<Int> get() =_dialogTimer

    var totalIdleTime=MutableLiveData(0)

    private val handler = Handler(Looper.getMainLooper())
    private var isIdleRunning=false
    private var isDialogRunning=false
    private val runnable = object : Runnable {
        override fun run() {
            // Increment timer value
            _idletimer.postValue(_idletimer.value?.plus(1))
            // Schedule the runnable again after 1 second
            handler.postDelayed(this, 1000)
        }
    }
    private val dialogRunnable = object : Runnable{
        override fun run() {
            _dialogTimer.postValue(_dialogTimer.value?.plus(1))
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

    fun startDialogTimer(){
        handler.post(dialogRunnable)
        isDialogRunning =true
    }
    fun isDialogRunning():Boolean{
        return isDialogRunning;
    }
fun getDialogTimer():Int?{
    return dialogTimer.value;
}
    fun stopDialogTimer(){
        handler.removeCallbacks(dialogRunnable)
        _idletimer.value =8
        isDialogRunning =false
    }
}