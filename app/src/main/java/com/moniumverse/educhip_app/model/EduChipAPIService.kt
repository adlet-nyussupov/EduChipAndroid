package com.moniumverse.educhip_app.model

import com.google.gson.GsonBuilder
import com.moniumverse.educhip_app.model.opportunities.OpportunitiesAPI
import com.moniumverse.educhip_app.model.opportunities.OpportunitiesModel
import com.moniumverse.educhip_app.model.user.UserModel
import com.moniumverse.educhip_app.model.user.UserSigninModel
import com.moniumverse.educhip_app.model.user.UsersAPI
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class EduChipAPIService {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val BASE_URL = "http://10.0.2.2:8080/";
    private val userApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(UsersAPI::class.java)

    private val opportunitiesApi =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(OpportunitiesAPI::class.java)

    fun sendRequestForRegister(userModel: UserModel): Single<UserModel> {
        return userApi.sendRequestForRegister(userModel)
    }

    fun sendRequestForSignin(userSigninModel: UserSigninModel): Observable<Response<ResponseBody>> {
        return userApi.sendRequestForSignin(userSigninModel)
    }


    fun getOpportunitiesForUser(authToken: String, userId: String, page: Int, limit: Int): Single<List<OpportunitiesModel>> {
        return opportunitiesApi.getOpportunities(authToken, userId, page, limit)
    }

    fun getUserData(userId: String, authToken: String): Single<UserModel> {
        return userApi.getUserData(userId, authToken)
    }


}