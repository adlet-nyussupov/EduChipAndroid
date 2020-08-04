package com.moniumverse.educhip_app.model

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(vararg user: User): List<Long>

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUser(userId: Int): User

    @Query("DELETE FROM user")
    suspend fun deleteUser()


}