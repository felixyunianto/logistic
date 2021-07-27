package com.adit.poskologistikapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bencana (
    @SerializedName("id") var id : String? = null,
    @SerializedName("nama") var nama : String? = null,
    @SerializedName("detail") var detail : String? = null,
    @SerializedName("tanggal") var tanggal : String? = null,
    @SerializedName("foto") var foto : String? = null,
) : Parcelable