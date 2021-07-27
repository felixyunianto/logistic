package com.adit.poskologistikapp.contracts

import com.adit.poskologistikapp.models.Bencana

interface HomeFragmentContract {
    interface HomeFragmentView {
        fun attachToSlider(bencana: List<Bencana>)
        fun showToast(message : String)
        fun showLoading()
        fun hideLoading()
    }

    interface HomeFragmentPresenter{
        fun infoBencana()
        fun destroy()
    }
}