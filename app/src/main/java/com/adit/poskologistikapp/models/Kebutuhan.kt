package com.adit.poskologistikapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kebutuhan (
    @SerializedName("id") var id : Int,
    @SerializedName("id_posko") var id_posko : Int,
    @SerializedName("posko") var posko : String,
    @SerializedName("id_produk") var id_produk : Int,
    @SerializedName("produk") var produk : String,
    @SerializedName("jenis_kebutuhan") var jenis_kebutuhan : String,
    @SerializedName("keterangan") var keterangan : String,
    @SerializedName("jumlah") var jumlah : Int,
    @SerializedName("tanggal") var tanggal : String,
    @SerializedName("status") var status : String,
    @SerializedName("satuan") var satuan : String,
) : Parcelable