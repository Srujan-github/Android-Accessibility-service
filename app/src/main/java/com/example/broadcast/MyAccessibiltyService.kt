package com.example.broadcast

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber


class MyAccessibilityService : AccessibilityService() {
    private val TAG ="check"


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event!=null){
            Timber.tag(TAG).d("Event " + event.eventType + " || " + event.eventTime)
       if(listenEvent) _clickCount.postValue(_clickCount.value?.plus(1))
        }
    }

    override fun onInterrupt() {

    }
    override fun onServiceConnected() {
         val info =AccessibilityServiceInfo()
        info.apply {
            eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED or AccessibilityEvent.TYPE_VIEW_FOCUSED or AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION or AccessibilityEvent.TYPE_VIEW_SCROLLED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN
            notificationTimeout = 100
        }
        Timber.tag(TAG).d("checkjj Connected")
        this.serviceInfo = info
        isServiceConnected=true

    }
companion object{
    private val _clickCount = MutableLiveData(0)
    val clickCount:LiveData<Int> get()= _clickCount
        var isServiceConnected=false
    var listenEvent =false
    fun startService(){
        listenEvent =true
    }
    fun stopService(){
        listenEvent =false
    }

}
}
