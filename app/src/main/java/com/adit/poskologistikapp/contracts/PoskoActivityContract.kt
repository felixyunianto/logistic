package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Posko

interface PoskoActivityContract {
    interface PoskoActivityView{
        fun attachToRecycler(posko : List<Posko>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface PoskoActivityPresenter{
        fun infoPosko()
        fun delete(token : String, id : String)
        fun destroy()
    }

    interface CreateOrUpdateView {
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun success()
    }

    interface CreateOrUpdateInteraction {
        fun create(token: String, nama : String, jumlah_pengungsi: String, kontak_hp: String, lokasi: String, longitude : String, latitude: String)
        fun update(token: String, id: String, nama : String, jumlah_pengungsi: String, kontak_hp: String, lokasi: String, longitude : String, latitude: String)
        fun destroy()
    }
}