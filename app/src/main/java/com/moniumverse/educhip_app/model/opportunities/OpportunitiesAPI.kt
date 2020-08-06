package com.moniumverse.educhip_app.model.opportunities

import io.reactivex.Single
import retrofit2.http.GET

interface OpportunitiesAPI {

    @GET("educhip-app-ws/opportunities")
    fun getOpportunities(): Single<List<OpportunitiesModel>>

}