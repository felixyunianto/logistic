package com.adit.poskologistikapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Posko(
    @SerializedName("id") var id : String,
    @SerializedName("nama") var nama : String,
    @SerializedName("jumlah_pengungsi") var jumlah_pengungsi : String,
    @SerializedName("kontak_hp") var kontak_hp : String,
    @SerializedName("lokasi") var lokasi : String,
    @SerializedName("latitude") var latitude : String,
    @SerializedName("longitude") var longitude : String,
    @SerializedName("created_at") var created_at : String,
    @SerializedName("updated_at") var updated_at : String,
) : Parcelable {
    override fun toString(): String {
        return nama!!
    }
}