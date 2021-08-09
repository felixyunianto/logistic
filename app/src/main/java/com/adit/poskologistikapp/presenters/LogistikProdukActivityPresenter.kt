package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.LogistikProdukActivityContract
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogistikProdukActivityPresenter(v : LogistikProdukActivityContract.LogistikProdukActivityView?) : LogistikProdukActivityContract.LogistikProdukActivityPresenter  {
    private var view : LogistikProdukActivityContract.LogistikProdukActivityView? = v
    private var apiService = APIClient.APIService()

    override fun getLogistikProduk(token: String) {
        val request = apiService.getLogistikProduk(token)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Logistik>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Logistik>>,
                response: Response<WrappedListResponse<Logistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
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

            override fun onFailure(call: Call<WrappedListResponse<Logistik>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun getLogistikProdukByPosko(id_posko: String) {
        val request = apiService.getLogistikProdukByPosko(id_posko)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Logistik>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Logistik>>,
                response: Response<WrappedListResponse<Logistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
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

            override fun onFailure(call: Call<WrappedListResponse<Logistik>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun deleteLogistik(token: String, id: String) {
        val request = apiService.deleteLogistikProduk(token, id)
        request.enqueue(object : Callback<WrappedResponse<Logistik>>{
            override fun onResponse(
                call: Call<WrappedResponse<Logistik>>,
                response: Response<WrappedResponse<Logistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.hideLoading()
                        getLogistikProduk(token)
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Logistik>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }
        })
    }

    override fun destroy() {
        view = null
    }

}