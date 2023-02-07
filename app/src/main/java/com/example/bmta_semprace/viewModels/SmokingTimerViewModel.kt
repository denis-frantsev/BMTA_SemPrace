package com.example.bmta_semprace.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.bmta_semprace.models.Relapse
import com.example.bmta_semprace.models.SmokingTimer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.Date

class SmokingTimerViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPref = application.getSharedPreferences("smoking_timer", Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()
    private val smokingTimerModel = SmokingTimer(sharedPref.getLong("start_time", 0))

    fun startTimer() {
        smokingTimerModel.start(System.currentTimeMillis())
        editor.putLong("start_time", System.currentTimeMillis()).apply()
    }

    fun stopTimer(relapseReason:String) {
        smokingTimerModel.stop()
        writeRelapseToFile(relapseReason)
        editor.putLong("start_time", 0).apply()
    }

    fun getElapsedTime() = smokingTimerModel.elapsedTime

    private fun writeRelapseToFile(relapseReason:String) {
        val relapse = Relapse(Date(), relapseReason)

        val relapses = mutableListOf<Relapse>()

        val file = File(getApplication<Application>().filesDir, "relapse_history.json")
        if (file.exists()) {
            val json = file.readText()
            if (json.isNotEmpty())
                relapses.addAll(Gson().fromJson(json, object : TypeToken<List<Relapse>>() {}.type))
        }
        relapses.add(relapse)
        val output = Gson().toJson(relapses)
        file.writeText(output)
    }
}