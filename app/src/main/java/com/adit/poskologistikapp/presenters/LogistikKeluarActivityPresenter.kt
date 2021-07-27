package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.LogistikKeluarActivityContract
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogistikKeluarActivityPresenter(v : LogistikKeluarActivityContract.LogistikKeluarActivityView?) : LogistikKeluarActivityContract.LogistikKeluarActivityPresenter {
    private var view : LogistikKeluarActivityContract.LogistikKeluarActivityView? = v
    private var apiService = APIClient.APIService()

    override fun getLogistikKeluar(token: String) {
        val request = apiService.getLogistikKeluar(token)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<LogistikKeluar>>{
            override fun onResponse(
                call: Call<WrappedListResponse<LogistikKeluar>>,
                response: Response<WrappedListResponse<LogistikKeluar>>
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
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<LogistikKeluar>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun hapusLogistikKeluar(token: String, id: String) {
        val request = apiService.hapusLogistikKeluar(token, id)
        request.enqueue(object : Callback<WrappedResponse<LogistikKeluar>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikKeluar>>,
                response: Response<WrappedResponse<LogistikKeluar>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        getLogistikKeluar(token)
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikKeluar>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }
}