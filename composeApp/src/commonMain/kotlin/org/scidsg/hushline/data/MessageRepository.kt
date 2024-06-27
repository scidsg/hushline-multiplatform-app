package org.scidsg.hushline.data

import kotlinx.coroutines.flow.Flow
import org.scidsg.hushline.data.database.MessageDao
import org.scidsg.hushline.data.database.MessageEntity

class MessageRepository(private val messageDao: MessageDao) {

    suspend fun getMessages(): Flow<List<MessageEntity>> {
        return messageDao.getAllMessages()
    }
}