package com.example.bmta_semprace.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bmta_semprace.models.Relapse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import org.json.JSONException
import java.io.File

class RelapsesViewModel(application: Application) : AndroidViewModel(application) {

    private val _relapseList = MutableLiveData<List<Relapse>>()
    val relapseList: MutableLiveData<List<Relapse>>
        get() = _relapseList

    init {
        loadRelapseListFromJsonFile()
    }

    private fun loadRelapseListFromJsonFile() {
        val file = File(getApplication<Application>().filesDir, "relapse_history.json")
        val relapses = mutableListOf<Relapse>()
        if (file.exists()) {
            val json = file.readText()
            relapses.addAll(Gson().fromJson(json, object : TypeToken<List<Relapse>>() {}.type))
        }
        relapseList.value = relapses
    }

    fun updateRelapseList() = loadRelapseListFromJsonFile()

}