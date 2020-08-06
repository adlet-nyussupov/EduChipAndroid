package com.moniumverse.educhip_app.model.opportunities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class OpportunitiesModel(

    @ColumnInfo(name = "opportunityId")
    @SerializedName("opportunityId")
    val opportunityId : String?,

    @ColumnInfo(name = "opportunityName")
    @SerializedName("opportunityName")
    val opportunityName : String?,

    @ColumnInfo(name = "opportunityDeadline")
    @SerializedName("opportunityDeadline")
    val opportunityDeadline: String?,

    @ColumnInfo(name = "opportunityDescription")
    @SerializedName("opportunityDescription")
    val opportunityDescription: String?,

    @ColumnInfo(name = "opportunityPeriod")
    @SerializedName("opportunityPeriod")
    val opportunityPeriod : String?,

    @ColumnInfo(name = "opportunityImageUrl")
    @SerializedName("opportunityImageUrl")
    val opportunityImageUrl: String?,

    @ColumnInfo(name = "opportunity_url")
    @SerializedName("opportunity_url")
    val opportunity_url: String?
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}