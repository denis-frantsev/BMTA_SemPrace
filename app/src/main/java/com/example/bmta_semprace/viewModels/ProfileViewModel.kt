package com.example.bmta_semprace.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bmta_semprace.models.Smoker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val _smoker = MutableLiveData<Smoker>().apply {
        value = readUserJson()
    }
    val smoker: LiveData<Smoker> = _smoker

    private fun readUserJson(): Smoker {
        val file = File(getApplication<Application>().filesDir, "data.json")
        var smoker: Smoker? = null
        if (file.exists()) {
            val json = file.readText()
            smoker = Gson().fromJson(json, object : TypeToken<Smoker>() {}.type)
        }
        return smoker!!
    }

    fun updateSmokerDataJson(smoker: Smoker, context: Context) {
        var jsonObject = JSONObject()
        jsonObject.put("name", smoker.name)
        jsonObject.put("packPrice", smoker.packPrice)
        jsonObject.put("cigsPerDay", smoker.cigsPerDay)
        if (!smoker.motivation.isNullOrEmpty())
            jsonObject.put("motivation", smoker.motivation)

        val userString: String = jsonObject.toString()
        val file = File(context.filesDir, "data.json")
        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(userString)
        bufferedWriter.close()

        _smoker.value = readUserJson()
    }
}