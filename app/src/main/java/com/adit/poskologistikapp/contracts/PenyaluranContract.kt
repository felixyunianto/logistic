package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.Penerimaan
import com.adit.poskologistikapp.models.Penyaluran
import com.adit.poskologistikapp.models.Posko

interface PenyaluranContract {
    interface  View{
        fun attachToRecycler(penyaluran : List<Penyaluran>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface presenter{
        fun getLogistikProduk(token : String)
        //        fun deleteLogistik(token: String, id : String)
        fun destroy()
    }

    interface CreateOrUpdateView {
        fun attachToSpinner(produk : List<Logistik>)
        fun showToast(message: String)
        fun showLoading()
        fun hideLoading()
        fun success()
    }

    interface CreateOrUpdatePresenter{
        fun getProduk(token : String)
        fun create(token : String, jenis_kebutuhan : String, keterangan : String, jumlah : String, status : String, satuan : String, tanggal : String, id_produk : String, penerima : String)
        fun edit(token : String, id : String, jenis_kebutuhan : String, keterangan : String, jumlah : String, status : String, satuan : String, tanggal : String, id_produk : String, penerima : String)
        fun destroy()
    }
}