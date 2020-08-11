package com.moniumverse.educhip_app.model.opportunities

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface OpportunitiesAPI {

    @GET("educhip-app-ws/opportunities/{id}")
    fun getOpportunities(
        @Header(value = "Authorization") authToken: String,
        @Path("id") userId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Single<List<OpportunitiesModel>>

}