package com.moniumverse.educhip_app.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserAuthTokenModel(

    @ColumnInfo(name = "userOwnerId")
    @SerializedName("userOwnerId")
    val userOwnerId: String
    ,

    @ColumnInfo(name = "userToken")
    @SerializedName("userToken")
    val userToken: String

){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 1
}