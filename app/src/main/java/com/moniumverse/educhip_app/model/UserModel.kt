package com.moniumverse.educhip_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(

    @ColumnInfo(name = "age")
    @SerializedName("age")
    val userAge: String?,

    @ColumnInfo(name = "applyingEducationDegree")
    @SerializedName("applyingEducationDegree")
    val userApplyingDegree: String?,

    @ColumnInfo(name = "currentEducationDegree")
    @SerializedName("currentEducationDegree")
    val userCurrentDegree: String?,

    @ColumnInfo(name = "email")
    @SerializedName("email")
    val userEmail: String,

    @ColumnInfo(name = "firstName")
    @SerializedName("firstName")
    val userFirstName: String,

    @ColumnInfo(name = "lastName")
    @SerializedName("lastName")
    val userLastname: String,

    @ColumnInfo(name = "password")
    @SerializedName("password")
    val userPassword: String,

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    val userPhone: String,

    @ColumnInfo(name = "whereToStudy")
    @SerializedName("whereToStudy")
    val userWhereToStudy: String
){
    @PrimaryKey(autoGenerate = false)
    var userId: Int = 0

}