package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.BencanaActivityContract
import com.adit.poskologistikapp.models.Bencana
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BencanaActivityPresenter(v : BencanaActivityContract.BencanaActivityView) : BencanaActivityContract.BencanaActivityPresenter {
    private var view : BencanaActivityContract.BencanaActivityView? = v
    private var apiService = APIClient.APIService()
    override fun infoBencana() {
        val request = apiService.infoBencana()
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Bencana>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Bencana>>,
                response: Response<WrappedListResponse<Bencana>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.isNotEmpty()){
                            view?.attachToRecycle(body.data)
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

            override fun onFailure(call: Call<WrappedListResponse<Bencana>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun delete(token: String, id: String) {
        val request = apiService.deleteBencana(token, id.toInt())
        request.enqueue(object : Callback<WrappedResponse<Bencana>>{
            override fun onResponse(
                call: Call<WrappedResponse<Bencana>>,
                response: Response<WrappedResponse<Bencana>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        infoBencana()
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }

                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Bencana>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun destroy() {
        view = null
    }

}