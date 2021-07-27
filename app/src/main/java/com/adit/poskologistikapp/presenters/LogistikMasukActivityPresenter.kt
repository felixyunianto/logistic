package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.LogistikMasukActivityContract
import com.adit.poskologistikapp.models.LogistikMasuk
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogistikMasukActivityPresenter(v: LogistikMasukActivityContract.LogistikMasukActivityView) : LogistikMasukActivityContract.LogistikMasukActivityPresenter {
    private var view : LogistikMasukActivityContract.LogistikMasukActivityView? = v
    private var apiService = APIClient.APIService()

    override fun getLogistikMasuk(token: String) {
        val request = apiService.getLogistikMasuk(token)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<LogistikMasuk>>{
            override fun onResponse(
                call: Call<WrappedListResponse<LogistikMasuk>>,
                response: Response<WrappedListResponse<LogistikMasuk>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.isNotEmpty()){
                            view?.attachToRecycler(body.data)
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

            override fun onFailure(call: Call<WrappedListResponse<LogistikMasuk>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun delete(token: String, id: String) {
        val request = apiService.deleteLogistikMasuk(token, id)
        request.enqueue(object : Callback<WrappedResponse<LogistikMasuk>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikMasuk>>,
                response: Response<WrappedResponse<LogistikMasuk>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        getLogistikMasuk(token)
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikMasuk>>, t: Throwable) {
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