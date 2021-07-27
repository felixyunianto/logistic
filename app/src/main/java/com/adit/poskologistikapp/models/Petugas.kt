package com.adit.poskologistikapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Petugas(
    @SerializedName("id") var id : String,
    @SerializedName("username") var username : String? = null,
    @SerializedName("password") var password : String?  = null,
    @SerializedName("level") var level : String,
    @SerializedName("id_posko") var id_posko : String,
    @SerializedName("nama_posko") var nama_posko : String,
    @SerializedName("created_at") var created_at : String,
    @SerializedName("updated_at") var updated_at : String,
) : Parcelable