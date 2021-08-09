package com.adit.poskologistikapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Penyaluran(
    @SerializedName("id") var id : String,
    @SerializedName("jenis_kebutuhan") var jenis_kebutuhan : String,
    @SerializedName("keterangan") var keterangan : String,
    @SerializedName("jumlah") var jumlah : String,
    @SerializedName("status") var status : String,
    @SerializedName("pengirim_id") var pengirim_id : String,
    @SerializedName("nama_pengirim") var nama_pengirim : String,
    @SerializedName("satuan") var satuan : String,
    @SerializedName("tanggal") var tanggal : String,
    @SerializedName("id_produk") var id_produk : String,
    @SerializedName("nama_produk") var nama_produk : String,
    @SerializedName("penerima") var penerima : String,
) : Parcelable
