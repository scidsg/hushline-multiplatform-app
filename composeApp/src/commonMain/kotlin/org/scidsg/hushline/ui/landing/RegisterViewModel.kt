package org.scidsg.hushline.ui.landing

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.scidsg.hushline.data.UserRepository

class RegisterViewModel(private val userRepo: UserRepository): ScreenModel {

    @OptIn(DelicateCoroutinesApi::class)
    private val scope = CoroutineScope(GlobalScope.coroutineContext)

    fun register(username: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (Throwable) -> Unit) {
        scope.launch {
            try {
                val response = userRepo.register(username, password)
                onLoginSuccess()
            } catch (e: Exception) {
                onLoginError(e)
            }
        }
    }
}