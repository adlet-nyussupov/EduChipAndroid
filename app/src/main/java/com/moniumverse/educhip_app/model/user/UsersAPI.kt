package com.moniumverse.educhip_app.model.user

import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response;
import retrofit2.http.*

interface UsersAPI {

    @POST("educhip-app-ws/users")
    fun sendRequestForRegister(@Body userModel: UserModel): Single<UserModel>


    @POST("educhip-app-ws/users/login")
    fun sendRequestForSignin(@Body userSigninModel: UserSigninModel): Observable<Response<ResponseBody>>

    @GET("educhip-app-ws/users/{id}")
    fun getUserData(
        @Path("id") userId: String,
        @Header(value = "Authorization") authToken: String
    ): Single<UserModel>
}