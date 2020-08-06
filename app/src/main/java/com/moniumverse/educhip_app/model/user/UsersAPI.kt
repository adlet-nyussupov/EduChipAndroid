package com.moniumverse.educhip_app.model.user

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersAPI {

    @POST("educhip-app-ws/users")
    fun sendRequestForRegister(@Body userModel: UserModel): Single<UserModel>

}