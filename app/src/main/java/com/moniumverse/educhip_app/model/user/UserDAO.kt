package com.moniumverse.educhip_app.model.user

import androidx.room.*

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(vararg user: UserModel)

    @Query("SELECT * FROM usermodel WHERE userId = :userId")
    suspend fun getUser(userId: Int): UserModel

    @Query("DELETE FROM usermodel")
    suspend fun deleteUser()


}