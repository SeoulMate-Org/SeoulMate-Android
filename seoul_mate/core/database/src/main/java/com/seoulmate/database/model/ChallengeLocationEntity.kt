package com.seoulmate.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (
    tableName = "challengeLocation"
)
data class ChallengeLocationEntity(
    @PrimaryKey(autoGenerate = true)
    val key: String,
    val latitude: Double,
    val longitude: Double,
)