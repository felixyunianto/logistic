package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.LogistikMasuk
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface LogistikMasukActivityContract {
    interface LogistikMasukActivityView {
        fun attachToRecycler(logistik_masuk : List<LogistikMasuk>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface LogistikMasukActivityPresenter{
        fun getLogistikMasuk(token : String)
        fun delete(token : String, id : String)
        fun destroy()
    }

    interface CreateOrUpdateView{
        fun attachToSpinner(produk : List<Logistik>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun success();
    }

    interface CreateOrUpdatePresenter{
        fun create(token : String, jenis_kebutuhan : RequestBody,keterangan : RequestBody, jumlah : RequestBody, pengirim : RequestBody, satuan : RequestBody, status : RequestBody, tanggal : RequestBody, foto : MultipartBody.Part, id_produk : RequestBody)
        fun createNew(token : String, jenis_kebutuhan : RequestBody,keterangan : RequestBody, jumlah : RequestBody, pengirim : RequestBody, satuan : RequestBody, status : RequestBody, tanggal : RequestBody, foto : MultipartBody.Part, baru : RequestBody, nama_produk :RequestBody)
        fun update(token : String, id : String, jenis_kebutuhan : RequestBody,keterangan : RequestBody, jumlah : RequestBody, pengirim : RequestBody, satuan : RequestBody, status : RequestBody, tanggal : RequestBody, foto : MultipartBody.Part, id_produk : RequestBody, _method : RequestBody)
        fun updateTanpaFoto(token : String, id : String, jenis_kebutuhan : RequestBody,keterangan : RequestBody, jumlah : RequestBody, pengirim : RequestBody, satuan : RequestBody, status : RequestBody, tanggal : RequestBody, id_produk : RequestBody, _method : RequestBody)
        fun getLogistikProduk(token : String)
        fun destroy()
    }
}