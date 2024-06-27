package org.scidsg.hushline.ui.landing

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import org.scidsg.hushline.data.database.UserEntity
import org.scidsg.hushline.data.UserRepository
import org.scidsg.hushline.getCurrentDate

class LoginViewModel(private val userRepo: UserRepository): ScreenModel {

    @OptIn(DelicateCoroutinesApi::class)
    private val scope = CoroutineScope(coroutineContext)

    private var currentUser: UserEntity? = null

    fun checkLoggedInUser(onResult: (Boolean) -> Unit) {
        scope.launch {
            currentUser = userRepo.getLoggedInUser()
            onResult(currentUser != null)
        }
    }

    fun login(username: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (Throwable) -> Unit) {
        scope.launch {
            try {
                val response = userRepo.login(username, password)
                val user = UserEntity(
                    id = 1,
                    jwtToken = response.data.token,
                    jwtStatus = "active",
                    loggedOut = false,
                    created = getCurrentDate(),
                    updated = getCurrentDate()
                )
                userRepo.newUser(user)
                currentUser = user
                onLoginSuccess()
            } catch (e: Exception) {
                onLoginError(e)
            }
        }
    }

    suspend fun isUserLoggedIn(): Boolean {
        return userRepo.isUserLoggedIn()
    }
}