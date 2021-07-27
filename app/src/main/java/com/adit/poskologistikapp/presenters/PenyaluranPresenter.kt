package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.PenyaluranContract
import com.adit.poskologistikapp.models.Penyaluran
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenyaluranPresenter(v : PenyaluranContract.View) : PenyaluranContract.presenter {
    private var view : PenyaluranContract.View? = v
    private var apiService = APIClient.APIService()
    override fun getLogistikProduk(token: String) {
        val request = apiService.getPenyaluran(token)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Penyaluran>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Penyaluran>>,
                response: Response<WrappedListResponse<Penyaluran>>
            ) {
                if(response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        if (body.data.isNotEmpty()) {
                            view?.attachToRecycler(body.data)
                        } else {
                            view?.emptyData()

                        }
                    }
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<Penyaluran>>, t: Throwable) {
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