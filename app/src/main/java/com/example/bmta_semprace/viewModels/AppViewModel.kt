package com.example.bmta_semprace.viewModels

import android.util.Log
import com.example.bmta_semprace.models.Smoker
import org.json.JSONException
import org.json.JSONObject

class AppViewModel(jsonString: String) {
    var smoker: Smoker = Smoker("", 0, 0)

    init {
        var objJson = parseJsonObject(jsonString)
        if (objJson != null) {

            smoker = Smoker(
                objJson.getString("name"),
                objJson.getInt("cigsPerDay"),
                objJson.getInt("packPrice")
            )
            try {
                if (!objJson.getString("motivation").isNullOrEmpty()) {
                    smoker.motivation = objJson.getString("motivation")
                }
            } catch (_:JSONException){
                smoker.motivation = null
            }
        }
    }

    private fun parseJsonObject(dataJson: String): JSONObject? {
        try {
            return JSONObject(dataJson)
        } catch (ex: JSONException) {
            Log.i("JsonParser object", "unexpected JSON exception", ex)
            return null
        }
    }

}