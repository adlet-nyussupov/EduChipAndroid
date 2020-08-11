package com.moniumverse.educhip_app.model.user

import android.icu.text.DateTimePatternGenerator.PatternInfo.CONFLICT
import androidx.room.*
import retrofit2.http.GET

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(vararg user: UserModel)

    @Query("SELECT * FROM usermodel WHERE userId = :userId")
    suspend fun getUser(userId: String): UserModel

    @Query("DELETE FROM usermodel")
    suspend fun deleteUser(): Int

    @Query("DELETE FROM userauthtokenmodel")
    suspend fun deleteUserToken(): Int

    @Transaction
    @Query("REPLACE INTO userauthtokenmodel ( id, userToken, userOwnerId) VALUES (1, :userToken, :userId)")
    suspend fun insertTokenToUser(userId: String, userToken: String): Long

    @Transaction
    @Query("SELECT * FROM userauthtokenmodel")
    suspend fun getToken(): List<UserAuthTokenModel>

}