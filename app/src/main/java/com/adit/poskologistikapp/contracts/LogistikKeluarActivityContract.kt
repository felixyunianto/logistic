package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.models.Posko

interface LogistikKeluarActivityContract {
    interface LogistikKeluarActivityView {
        fun attachToRecycler(logistik_keluar : List<LogistikKeluar>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface LogistikKeluarActivityPresenter {
        fun getLogistikKeluar(token : String)
        fun hapusLogistikKeluar(token : String, id : String)
        fun destroy()
    }

    interface CreateOrUpdateView {
        fun attachToSpinner(posko : List<Posko>)
        fun attachToSpinnerProduk(produk : List<Logistik>)
        fun showToast(message: String)
        fun showLoading()
        fun hideLoading()
        fun success()
    }

    interface CreateOrUpdatePresenter{
        fun getProduk(token: String)
        fun getPosko()
        fun create(token : String, jenis_kebutuhan : String, keterangan : String, jumlah : String, status : String, satuan : String, tanggal : String, id_produk : String, penerima_id : String)
        fun edit(token : String, id : String, jenis_kebutuhan : String, keterangan : String, jumlah : String, status : String, satuan : String, tanggal : String, id_produk : String, penerima_id : String)
        fun destroy()
    }
}