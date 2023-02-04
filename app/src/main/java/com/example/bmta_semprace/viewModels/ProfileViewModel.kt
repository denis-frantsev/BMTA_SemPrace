package com.example.bmta_semprace.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bmta_semprace.models.Smoker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val _smoker = MutableLiveData<Smoker>().apply {
        value = readUserJson()
    }
    val smoker: LiveData<Smoker> = _smoker

    private fun readUserJson(): Smoker {
        val file = File(getApplication<Application>().filesDir, "data.json")
        var smoker:Smoker? = null
        if (file.exists()) {
            val json = file.readText()
            smoker = Gson().fromJson(json, object : TypeToken<Smoker>() {}.type)
        }
        return smoker!!
    }
}