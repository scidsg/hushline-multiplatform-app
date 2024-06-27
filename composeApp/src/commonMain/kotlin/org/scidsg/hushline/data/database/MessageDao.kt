package org.scidsg.hushline.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert
    suspend fun saveMessage(message: MessageEntity)

    @Insert
    suspend fun saveMessages(messages: List<MessageEntity>)

    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun getMessageById(id: Int = 1): MessageEntity

    @Query("SELECT * FROM messages")
    suspend fun getAllMessages(): Flow<List<MessageEntity>>

    @Delete
    suspend fun deleteMessage(message: MessageEntity)

    @Delete
    suspend fun deleteAllMessages(messages: List<MessageEntity>)
}