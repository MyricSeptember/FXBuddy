package com.example.fxbuddy.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fx_table")
data class DatabaseRate(
    @PrimaryKey
    val date: String,
    val close: Double,
    val high: Double,
    val low: Double,
    val open: Double
)