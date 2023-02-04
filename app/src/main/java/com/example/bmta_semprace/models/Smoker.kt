package com.example.bmta_semprace.models

import kotlin.math.floor

data class Smoker(
    var name: String,
    var cigsPerDay: Int,
    var packPrice: Int
) {

    var cigsNotSmoked: Int = 0
    var packs: Int = 0
    var moneySaved: Int = 0
    var motivation: String? = null

    private var elapsedTime: Long = 0
        set(value) {
            field = value
        }

    fun updateElapsedTime(time: Long) {
        elapsedTime = time
        var elapsedDays: Double = elapsedTime.toDouble() / 86400000
        moneySaved = (elapsedDays * cigsPerDay * packPrice / 20).toInt()
        cigsNotSmoked = (elapsedDays * cigsPerDay).toInt()
        if (cigsNotSmoked > 0)
            floor(20.0 / cigsNotSmoked).toInt()
    }
}