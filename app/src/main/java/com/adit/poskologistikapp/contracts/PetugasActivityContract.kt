package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Petugas
import com.adit.poskologistikapp.models.Posko

interface PetugasActivityContract {
    interface PetugasActivityView {
        fun showToast(message : String)
        fun attachPetugasRecycler(petugas : List<Petugas>)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface PetugasActivityPresenter {
        fun infoPetugas(token : String)
        fun delete(token:String, id: String,)
        fun destroy()
    }

    interface CreateOrUpdateView {
        fun attachToSpinner(posko: List<Posko>)
        fun setSelectionSpinner(posko: List<Posko>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun success();
    }

    interface CreateOrUpdatePresenter{
        fun create(token:String, username: String, password: String, confirm_password: String, level: String, id_posko : String)
        fun update(token:String, id: String, username: String, password: String, confirm_password: String, level: String, id_posko : String)
        fun getPosko()
        fun destroy()
    }
}