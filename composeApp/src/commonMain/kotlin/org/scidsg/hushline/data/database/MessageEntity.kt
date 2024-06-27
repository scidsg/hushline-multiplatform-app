package org.scidsg.hushline.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val message: String,
    val created: String,
    val updated: String
)
