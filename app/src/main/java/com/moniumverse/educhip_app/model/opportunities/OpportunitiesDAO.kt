package com.moniumverse.educhip_app.model.opportunities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface OpportunitiesDAO {

    @Insert
    suspend fun insertALL(vararg opportunities: OpportunitiesModel): List<Long>

    @Query("SELECT * FROM opportunitiesmodel")
    suspend fun getAllDogs(): List<OpportunitiesModel>

    @Query("SELECT * FROM opportunitiesmodel WHERE id = :id")
    suspend fun getDog(id: Int): OpportunitiesModel

    @Query("DELETE FROM opportunitiesmodel")
    suspend fun deleteAllOpportunities()

}