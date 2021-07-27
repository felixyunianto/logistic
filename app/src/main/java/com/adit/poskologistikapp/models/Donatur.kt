package com.adit.poskologistikapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Donatur(
    @SerializedName("id") var id : String? = null,
    @SerializedName("nama") var nama : String? = null,
    @SerializedName("jenis_kebutuhan") var jenis_kebutuhan : String? = null,
    @SerializedName("keterangan") var keterangan : String? = null,
    @SerializedName("alamat") var alamat : String? = null,
    @SerializedName("id_posko") var id_posko : String? = null,
    @SerializedName("posko_penerima") var posko_penerima : String? = null,
    @SerializedName("tanggal") var tanggal : String? = null,
    @SerializedName("jumlah") var jumlah : String? = null,
    @SerializedName("satuan") var satuan : String? = null,
    @SerializedName("created_at") var created_at : String? = null,
    @SerializedName("updated_at") var updated_at : String? = null,
) : Parcelable
