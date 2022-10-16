package com.example.move.history.historyviewmodel.repository

import com.example.move.model.Weather
import com.example.move.model.romm.HistoryEntity

interface OnDeleteClickListener {
    fun onDelete(entity: HistoryEntity)
}