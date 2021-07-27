package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.HomeFragmentContract
import com.adit.poskologistikapp.models.Bencana
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentPresenter(v : HomeFragmentContract.HomeFragmentView): HomeFragmentContract.HomeFragmentPresenter {
    private var view : HomeFragmentContract.HomeFragmentView? = v
    private var apiServices = APIClient.APIService()

    override fun infoBencana() {
        val request = apiServices.infoBencana()
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Bencana>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Bencana>>,
                response: Response<WrappedListResponse<Bencana>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.attachToSlider(body.data)
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<Bencana>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }
        })
    }

    override fun destroy() {
        view = null
    }
}