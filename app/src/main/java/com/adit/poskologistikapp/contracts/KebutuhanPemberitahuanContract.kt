package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Bencana
import com.adit.poskologistikapp.models.Kebutuhan

interface KebutuhanPemberitahuanContract {
    interface KebutuhanActivityView {
        fun attachToRecycle(kebutuhan: List<Kebutuhan>)
        fun showToast(message: String)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface KebutuhanActivityPresenter {
        fun infoBencana()
        fun destroy()
    }
}