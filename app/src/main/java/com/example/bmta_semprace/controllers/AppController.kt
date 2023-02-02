package com.example.bmta_semprace.controllers

import android.util.Log
import com.example.bmta_semprace.models.Smoker
import org.json.JSONException
import org.json.JSONObject

class AppController(jsonString: String) {
    lateinit var smoker: Smoker

    init {
        var objJson = parseJsonObject(jsonString, "smoker")
        if (objJson != null) {
            smoker = Smoker(
                objJson.getString("name"),
                objJson.getInt("cigsPerDay"),
                objJson.getInt("packPrice")
            )
        }
    }

    private fun parseJsonObject(dataJson: String, item: String): JSONObject? {
        try {
            return JSONObject(dataJson)
        } catch (ex: JSONException) {
            Log.i("JsonParser object", "unexpected JSON exception", ex)
            return null
        }
    }

}