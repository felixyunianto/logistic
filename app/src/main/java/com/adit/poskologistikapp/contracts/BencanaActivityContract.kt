package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Bencana
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface BencanaActivityContract {
    interface BencanaActivityView {
        fun attachToRecycle(bencana: List<Bencana>)
        fun showToast(message: String)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface BencanaActivityPresenter {
        fun infoBencana()
        fun delete(token:String, id: String)
        fun destroy()
    }

    interface CreateOrUpdateView {
        fun showToast(message: String?)
        fun showLoading()
        fun hideLoading()
        fun success()
    }

    interface CreateOrUpdatePresenter {
        fun create(token: String, name: RequestBody, foto: MultipartBody.Part, detail : RequestBody, date: RequestBody)
        fun update(token: String, id: String, name: RequestBody, foto: MultipartBody.Part, detail : RequestBody, date: RequestBody, method : RequestBody)
        fun updateTanpaFoto(token: String, id: String, name: RequestBody, detail : RequestBody, date: RequestBody, method : RequestBody)
        fun destroy()
    }
}