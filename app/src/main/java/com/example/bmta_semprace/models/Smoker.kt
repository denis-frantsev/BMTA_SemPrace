package com.example.bmta_semprace.models

import kotlin.math.floor

data class Smoker(
    var name: String,
    var cigsPerDay: Int,
    var packPrice: Int
) {

    var cigsNotSmoked: Int = 0
    var packs: Int = 0
        get() = floor(20.0 / cigsNotSmoked).toInt()
    var moneySaved: Long = 0
        get() = elapsedTime * cigsPerDay * packPrice / (24 * 60 * 60 * 1000)

    private var elapsedTime: Long = 0
        set(value) {
            field = value
            moneySaved = elapsedTime * cigsPerDay * packPrice / (24 * 60 * 60 * 1000)
        }

    fun updateElapsedTime(time: Long) {
        elapsedTime = time
    }
}