package com.example.innospace.features.explore.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.innospace.features.explore.data.local.dao.OpportunityDao
import com.example.innospace.features.explore.data.local.models.OpportunityEntity

@Database(entities = [OpportunityEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun opportunityDao(): OpportunityDao
}
