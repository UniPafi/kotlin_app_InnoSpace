package com.example.innospace.features.profile.data.repositories

import com.example.innospace.features.profile.data.remote.ProfileService
import com.example.innospace.features.profile.data.remote.models.UpdateProfileDto
import com.example.innospace.features.profile.domain.models.StudentProfile
import com.example.innospace.features.profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileService: ProfileService
) : ProfileRepository {

    override suspend fun getStudentProfileByUserId(userId: Long): StudentProfile? = withContext(Dispatchers.IO) {
        try {
            val response = profileService.getAllStudentProfiles()

            if (response.isSuccessful) {
                response.body()?.let { profiles ->
                    val profileDto = profiles.find { it.userId == userId }

                    profileDto?.let {
                        return@withContext StudentProfile(
                            id = it.id,
                            userId = it.userId,
                            name = it.name,
                            photoUrl = it.photoUrl,
                            description = it.description,
                            phoneNumber = it.phoneNumber,
                            portfolioUrl = it.portfolioUrl,
                            skills = it.skills,
                            experiences = it.experiences
                        )
                    }
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun updateStudentProfile(
        profileId: Long,
        updateProfileDto: UpdateProfileDto
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = profileService.updateStudentProfile(profileId, updateProfileDto)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

