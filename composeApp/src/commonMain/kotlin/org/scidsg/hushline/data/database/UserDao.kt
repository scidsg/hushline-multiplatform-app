package org.scidsg.hushline.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Query("UPDATE users SET jwtToken = :token WHERE id = :id")
    suspend fun updateUserTokenById(id: Int = 1, token: String)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int = 1): UserEntity?

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Delete
    suspend fun deleteAllUsers(users: List<UserEntity>)
}