package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.*

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

    interface PoskoMasyarakatView {
        fun attachToRecycler(posko : List<Posko>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface PoskoMasyarakatPresenter{
        fun infoPosko()
        fun destroy()
    }

    interface PoskoMasyarakatDetailView{
        fun attachToRecylerMasuk(masuk : List<LogistikMasuk>)
        fun attachToRecyclerKeluar(keluar : List<LogistikKeluar>)
        fun attachToRecylerPenerimaan(penerimaan: List<Penerimaan>)
        fun attachToRecyclerPenyaluran(penyaluran : List<Penyaluran>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
    }

    interface PoskoMasyarakatDetailPresenter{
        fun masuk(token : String)
        fun keluar(id_posko : String)
        fun penerimaan(id_posko : String)
        fun penyaluran(id_posko : String)
        fun destroy()
    }
}