package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Kebutuhan
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.Posko

interface KebutuhanActivityContract {
    interface KebutuhanLogistikActivityView{
        fun showToast(message : String)
        fun attachKebutuhanLogistikRecycler(kebutuhan_logistik : List<Kebutuhan>)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface KebutuhanLogistikPresenter{
        fun infoKebutuhanLogistik(token : String, id_posko: String)
        fun delete(token : String, id: String)
        fun destroy()
    }

    interface CreateOrUpdateView {
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun success()
        fun attachToSpinner(produk: List<Logistik>)
    }

    interface CreateOrUpdateInteraction{
        fun create(token: String, id_produk : String, jenis_kebutuhan : String, keterangan : String, jumlah: String, status: String, tanggal : String, satuan : String,)
        fun update(token: String, id: String, id_produk : String, jenis_kebutuhan : String, keterangan : String, jumlah: String, status: String, tanggal : String, satuan : String,)
        fun getPosko(token: String)
        fun destroy()
    }
}