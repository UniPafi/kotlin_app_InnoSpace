package com.example.innospace.features.explore.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_opportunities")
data class OpportunityEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val summary: String,
    val description: String,
    val category: String,
    val requirements: String,
    val companyName: String,
    val companyDescription: String?,
    val companyLocation: String?,
    val companyPhotoUrl: String?
)