package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.DonaturActivityContract
import com.adit.poskologistikapp.models.Donatur
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonaturByPoskoPresenter(v : DonaturActivityContract.DonaturByPoskoView?) : DonaturActivityContract.DonaturByPoskoPresenter {
    private var view : DonaturActivityContract.DonaturByPoskoView? = v
    private var apiServices = APIClient.APIService()

    override fun infoDonaturByPosko(id_posko: String) {
        val request = apiServices.getDonaturByPosko(id_posko)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Donatur>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Donatur>>,
                response: Response<WrappedListResponse<Donatur>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.attachToRecycler(body.data)
                    }
                }else{
                    view?.showToast(response.message())
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<Donatur>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }
}