package com.moniumverse.educhip_app.model.user

import androidx.room.Embedded
import androidx.room.Relation

data class UserToToken(
    @Embedded val user: UserModel,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    )
    val token: UserAuthTokenModel
)