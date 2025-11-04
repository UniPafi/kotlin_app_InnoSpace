package com.example.innospace.features.explore.data.local.repositories

import com.example.innospace.features.explore.data.local.dao.OpportunityDao
import com.example.innospace.features.explore.data.local.models.OpportunityEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OpportunityLocalRepository @Inject constructor(
    private val dao: OpportunityDao
) {
    fun getAllFavorites(): Flow<List<OpportunityEntity>> = dao.getAllFlow()

    suspend fun addFavorite(opportunity: OpportunityEntity) = dao.insert(opportunity)

    suspend fun removeFavorite(opportunity: OpportunityEntity) = dao.delete(opportunity)

    suspend fun isFavorite(id: Long): Boolean = dao.isFavorite(id)
}
