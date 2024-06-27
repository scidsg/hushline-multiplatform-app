package org.scidsg.hushline.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jwt_tokens")
data class JWTTokenEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val jwtToken: String,
    val jwtStatus: String,
    val created: String,
    val updated: String
)
