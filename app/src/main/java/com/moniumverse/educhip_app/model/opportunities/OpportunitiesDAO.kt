package com.moniumverse.educhip_app.model.opportunities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface OpportunitiesDAO {

    @Insert
    suspend fun insertALL(vararg opportunities: OpportunitiesModel): List<Long>

    @Query("SELECT * FROM opportunitiesmodel")
    suspend fun getAllOpportunities(): List<OpportunitiesModel>

    @Query("SELECT * FROM opportunitiesmodel WHERE id = :id")
    suspend fun getOpportunity(id: Int): OpportunitiesModel

    @Query("DELETE FROM opportunitiesmodel")
    suspend fun deleteAllOpportunities()

}