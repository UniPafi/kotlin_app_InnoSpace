package com.example.innospace.features.explore.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.innospace.features.explore.data.local.models.OpportunityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OpportunityDao {
    @Query("SELECT * FROM saved_opportunities")
    fun getAllFlow(): Flow<List<OpportunityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(opportunity: OpportunityEntity)

    @Delete
    suspend fun delete(opportunity: OpportunityEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_opportunities WHERE id = :id)")
    suspend fun isFavorite(id: Long): Boolean
}
