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
    @Query("SELECT * FROM fx_table ORDER BY date")
    fun getAllRates(): Flow<List<DatabaseRate>>

    @Query("SELECT * FROM fx_table WHERE date = :date")
    fun getPlant(date: String): LiveData<DatabaseRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<DatabaseRate>)

    @Query("delete from fx_table")
    fun deleteAll()
}