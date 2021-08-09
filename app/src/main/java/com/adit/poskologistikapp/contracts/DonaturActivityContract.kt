package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Donatur
import com.adit.poskologistikapp.models.Posko

interface DonaturActivityContract {
    interface DonaturActivityView {
        fun showToast(message : String)
        fun attachDonaturRecycler(data_donatur : List<Donatur>)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface DonaturActivityPresenter {
        fun infoDonatur()
        fun delete(token: String, id : String)
        fun destroy()
    }

    interface CreateOrUpdateView{
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun attachToSpinner(posko : List<Posko>)
        fun success()
    }

    interface CreateOrUpdateInteraction{
        fun create(token: String, nama: String,  jenis_kebutuhan : String, keterangan : String, alamat: String, id_posko : String, tanggal : String, jumlah : String, satuan : String)
        fun update(token: String, id: String, nama: String,  jenis_kebutuhan : String, keterangan : String, alamat: String, id_posko : String, tanggal : String, jumlah : String, satuan : String)
        fun getPosko()
        fun destroy()
    }

    interface DonaturByPoskoView{
        fun attachToRecycler(donatur : List<Donatur>)
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface DonaturByPoskoPresenter {
        fun infoDonaturByPosko(id_posko : String)
        fun destroy()
    }
}