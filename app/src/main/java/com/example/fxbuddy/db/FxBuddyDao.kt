package com.example.fxbuddy.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fxbuddy.db.model.DatabaseRate
import kotlinx.coroutines.flow.Flow

@Dao
interface FxBuddyDao {
    @Query("SELECT * FROM history ORDER BY date")
    fun getAllRates(): Flow<List<DatabaseRate>>

    @Query("SELECT * FROM history WHERE date = :date")
    fun getPlant(date: String): LiveData<DatabaseRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<DatabaseRate>)

    @Query("delete from history")
    fun deleteAll()
}