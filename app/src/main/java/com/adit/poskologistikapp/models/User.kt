package com.adit.poskologistikapp.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id") val id : String,
    @SerializedName("id_posko") val id_posko : String,
    @SerializedName("username") val username : String,
    @SerializedName("level") val level : String,
    @SerializedName("token") val token : String,
    @SerializedName("device_token") val device_token : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
)