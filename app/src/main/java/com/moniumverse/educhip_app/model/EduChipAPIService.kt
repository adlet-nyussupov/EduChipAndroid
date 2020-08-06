package com.moniumverse.educhip_app.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EduChipAPIService {

    private val BASE_URL = "http://localhost:8080";
    private val api =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(EduChipUsersAPI::class.java)

    fun sendRequestForRegister(user : User): Single<User> {
        return api.sendRequestForRegister(user)
    }

}