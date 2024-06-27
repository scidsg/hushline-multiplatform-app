package org.scidsg.hushline.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import org.scidsg.hushline.common.C
import org.scidsg.hushline.data.database.UserDao
import org.scidsg.hushline.data.database.UserEntity
import org.scidsg.hushline.data.network.LoginRequest
import org.scidsg.hushline.data.network.LoginResponse
import org.scidsg.hushline.data.network.RegisterRequest
import org.scidsg.hushline.data.network.RegisterResponse
import org.scidsg.hushline.data.network.ResetRequest
import org.scidsg.hushline.data.network.ResetResponse

class UserRepository(private val userDao: UserDao, private val client: HttpClient) {

    suspend fun isUserLoggedIn(): Boolean {
        return userDao.getUserById(1)?.loggedOut?.not() ?: false
    }

    suspend fun getLoggedInUser(): UserEntity? {
        return userDao.getUserById(1)
    }

    suspend fun newUser(userEntity: UserEntity) {
        userDao.saveUser(userEntity)
    }

    suspend fun login(username: String, password: String): LoginResponse {
        return client.post("${C.BASE_URL}/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(username, password))
        }.body()
    }

    suspend fun register(username: String, password: String): RegisterResponse {
        return client.post("${C.BASE_URL}/register") {
            contentType(ContentType.Application.Json)
            setBody(RegisterRequest(username, password))
        }.body()
    }

    suspend fun sendResetLink(emailAddress: String): ResetResponse {
        return client.post("${C.BASE_URL}/reset-password") {
            contentType(ContentType.Application.Json)
            setBody(ResetRequest(emailAddress))
        }.body()
    }

    //Test
    suspend fun getUserInfo(token: String): UserEntity {
        return client.get("${C.BASE_URL}/userinfo") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }
}