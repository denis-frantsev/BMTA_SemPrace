package com.example.bmta_semprace.models

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import java.util.*

class SmokingTimerModel(private var startTime: Long) {
    private var endTime: Long = 0
    val elapsedTime = MutableLiveData<Long>()
    private val timer:Timer = Timer()
    val elapsedTimeString: LiveData<String> = Transformations.map(elapsedTime) { time ->
        DateUtils.formatElapsedTime(time / 1000)
    }

    init {
        if(startTime == 0L){
            start(System.currentTimeMillis())
        }
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                elapsedTime.postValue(System.currentTimeMillis() - startTime)
            }
        }, 0, 1000)
    }

    fun start(startTime: Long) {
        this.startTime = startTime
    }

    fun stop() {
        endTime = System.currentTimeMillis()
    }


}
