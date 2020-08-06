package com.moniumverse.educhip_app.model

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface EduChipUsersAPI {

    @POST("educhip-app-ws/users")
    fun sendRequestForRegister(@Body user: User): Single<User>

}