package com.example.innospace.features.profile.domain.repositories

import com.example.innospace.features.profile.data.remote.models.UpdateProfileDto
import com.example.innospace.features.profile.domain.models.StudentProfile

interface ProfileRepository {
    suspend fun getStudentProfileByUserId(userId: Long): StudentProfile?
    suspend fun updateStudentProfile(profileId: Long, updateProfileDto: UpdateProfileDto): Boolean
}

