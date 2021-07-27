package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.models.Penerimaan

interface PenerimaanKeluarFragmentContract {
    interface PenerimaanFragmentView{
        fun attachToRecycler(keluar : List<LogistikKeluar>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
        fun emptyData()
    }

    interface PenerimaanFragmentPresenter{
        fun infoPenerimaanKeluar(token : String)
        fun konfirmasi(token: String, id: String)
        fun destroy()
    }
}