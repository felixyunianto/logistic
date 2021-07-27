package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.DonaturActivityContract
import com.adit.poskologistikapp.models.Donatur
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonaturActivityPresenter(v : DonaturActivityContract.DonaturActivityView?) : DonaturActivityContract.DonaturActivityPresenter {
    private var view : DonaturActivityContract.DonaturActivityView? = v
    private var apiService = APIClient.APIService()
    override fun infoDonatur() {
        val request = apiService.getDonatur()
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Donatur>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Donatur>>,
                response: Response<WrappedListResponse<Donatur>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.isNotEmpty()){
                            view?.attachDonaturRecycler(body.data)
                        }else{
                            view?.emptyData()
                        }
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<Donatur>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun delete(token: String, id: String) {
        val request = apiService.hapusDonatur(token, id)
        with(request) {
            enqueue(object : Callback<WrappedResponse<Donatur>>{
                override fun onResponse(
                    call: Call<WrappedResponse<Donatur>>,
                    response: Response<WrappedResponse<Donatur>>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            view?.showToast(body.message)
                            infoDonatur()
                        }else{
                            view?.showToast(body?.message!!)
                        }
                    }else{
                        view?.showToast(response.message())
                    }
                    view?.hideLoading()
                }

                override fun onFailure(call: Call<WrappedResponse<Donatur>>, t: Throwable) {
                    view?.showToast("Tidak bisa koneksi ke server")
                    view?.hideLoading()
                    println(t.message)
                }

            })
        }
    }

    override fun destroy() {
        view = null
    }
}