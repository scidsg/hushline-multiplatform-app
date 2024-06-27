package org.scidsg.hushline.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val jwtToken: String,
    val jwtStatus: String,
    val loggedOut: Boolean,
    val created: String,
    val updated: String
)
