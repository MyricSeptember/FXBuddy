package com.example.fxbuddy.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class DatabaseRate(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val close: Double,
    val high: Double,
    val low: Double,
    val open: Double
)