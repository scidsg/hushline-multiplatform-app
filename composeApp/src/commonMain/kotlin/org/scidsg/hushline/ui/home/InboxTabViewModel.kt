package org.scidsg.hushline.ui.home

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.flow.Flow
import org.scidsg.hushline.data.MessageRepository
import org.scidsg.hushline.data.database.MessageEntity

class InboxTabViewModel(private val messageRepo: MessageRepository): ScreenModel {

    @OptIn(DelicateCoroutinesApi::class)
    private val scope = CoroutineScope(coroutineContext)

    suspend fun getMessages(): Flow<List<MessageEntity>> {
        return messageRepo.getMessages()
    }

    fun categorizeMessages(message: List<MessageEntity>): Flow<List<MessageEntity>> {

    }
}