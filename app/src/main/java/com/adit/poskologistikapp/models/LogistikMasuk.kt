package com.adit.poskologistikapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogistikMasuk(
    @SerializedName("id") val id : String,
    @SerializedName("jenis_kebutuhan") val jenis_kebutuhan : String,
    @SerializedName("keterangan") val keterangan : String,
    @SerializedName("jumlah") val jumlah : String,
    @SerializedName("status") val status : String,
    @SerializedName("pengirim") val pengirim : String,
    @SerializedName("satuan") val satuan : String,
    @SerializedName("tanggal") val tanggal : String,
    @SerializedName("foto") val foto : String,
    @SerializedName("id_produk") val id_produk : String,
    @SerializedName("nama_produk") val nama_produk : String,
) : Parcelable
