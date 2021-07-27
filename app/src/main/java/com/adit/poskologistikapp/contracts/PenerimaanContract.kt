package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.Penerimaan

interface PenerimaanContract {
    interface  View{
        fun attachToRecycler(penerimaan : List<Penerimaan>)
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
}