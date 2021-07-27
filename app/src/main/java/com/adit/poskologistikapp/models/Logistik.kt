package com.adit.poskologistikapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Logistik(
    @SerializedName("id") var id : String,
    @SerializedName("nama_produk") var nama_produk : String,
    @SerializedName("id_posko") var id_posko : String,
    @SerializedName("jumlah") var jumlah : String,
    @SerializedName("satuan") var satuan : String,
    @SerializedName("created_at") var created_at : String,
    @SerializedName("updated_at") var updated_at : String,
) : Parcelable {
    override fun toString(): String {
        return nama_produk!! + " " + satuan!!
    }
}
