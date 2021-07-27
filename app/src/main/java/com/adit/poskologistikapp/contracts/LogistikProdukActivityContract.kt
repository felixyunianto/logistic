package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Logistik

interface LogistikProdukActivityContract {
    interface LogistikProdukActivityView {
        fun attachToRecycler(logistik : List<Logistik>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface LogistikProdukActivityPresenter{
        fun getLogistikProduk(token : String)
        fun deleteLogistik(token: String, id : String)
        fun destroy()
    }

    interface CreateOrUpdateView{
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun success()
    }

    interface CreateOrUpdatePresenter{
        fun create(token : String, nama_produk : String, jumlah : String, satuan : String)
        fun update(token : String, id : String, nama_produk : String, jumlah : String, satuan : String)
        fun destroy(token : String, id : String)
    }
}