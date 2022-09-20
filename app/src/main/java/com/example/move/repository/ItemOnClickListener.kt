package com.example.move.repository

import com.example.move.model.Weather

interface ItemOnClickListener {
    fun itemOnClickListener(weather: Weather)
}