package org.scidsg.hushline.ui.landing

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import org.scidsg.hushline.data.UserRepository
import org.scidsg.hushline.isEmailAddressValid

class ForgotPasswordViewModel(private val userRepo: UserRepository): ScreenModel {

    @OptIn(DelicateCoroutinesApi::class)
    private val scope = CoroutineScope(coroutineContext)

    fun sendResetLink(emailAddress: String, onSendSuccess: () -> Unit, onSendError: (Throwable) -> Unit) {
        scope.launch {
            try {
                val response = userRepo.sendResetLink(emailAddress)
                onSendSuccess()
            } catch (e: Exception) {
                onSendError(e)
            }
        }
    }

    fun isValidEmailAddress(emailAddress: String): Boolean {
        return isEmailAddressValid(emailAddress)
    }
}