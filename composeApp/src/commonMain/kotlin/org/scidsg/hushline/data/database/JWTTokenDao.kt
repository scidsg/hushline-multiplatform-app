package org.scidsg.hushline.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JWTTokenDao {

    @Insert
    suspend fun saveToken(token: JWTTokenEntity)

    @Query("UPDATE jwt_tokens SET jwtToken = :token WHERE id = :id")
    suspend fun updateTokenById(id: Int = 1, token: String)

    @Query("SELECT * FROM jwt_tokens WHERE id = :id")
    suspend fun getTokenById(id: Int = 1): JWTTokenEntity

    @Query("SELECT * FROM jwt_tokens")
    fun getAllTokens(): Flow<List<JWTTokenEntity>>

    @Delete
    suspend fun deleteToken(token: JWTTokenEntity)

    @Delete
    suspend fun deleteAllTokens(tokens: List<JWTTokenEntity>)
}