package com.moniumverse.educhip_app.model

import com.moniumverse.educhip_app.model.opportunities.OpportunitiesAPI
import com.moniumverse.educhip_app.model.opportunities.OpportunitiesModel
import com.moniumverse.educhip_app.model.user.UserModel
import com.moniumverse.educhip_app.model.user.UsersAPI
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EduChipAPIService {

    private val BASE_URL = "http://10.0.2.2:8080/";
    private val userApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(UsersAPI::class.java)

    private val opportunitiesApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(OpportunitiesAPI::class.java)

    fun sendRequestForRegister(userModel: UserModel): Single<UserModel> {
        return userApi.sendRequestForRegister(userModel)
    }

    fun getOpportunitiesForUser(opportunitiesModel: OpportunitiesModel): Single<List<OpportunitiesModel>> {
        return opportunitiesApi.getOpportunities()
    }
}