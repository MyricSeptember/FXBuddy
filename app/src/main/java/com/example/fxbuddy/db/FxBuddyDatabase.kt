package com.example.fxbuddy.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fxbuddy.db.model.DatabaseRate

@Database(entities = [DatabaseRate::class], version = 1, exportSchema = false)
abstract class FxBuddyDataBase : RoomDatabase() {
    abstract fun fxRateDao(): FxBuddyDao
}