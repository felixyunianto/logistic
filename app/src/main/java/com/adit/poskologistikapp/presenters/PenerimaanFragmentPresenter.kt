package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.PenerimaanContract
import com.adit.poskologistikapp.models.Penerimaan
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenerimaanFragmentPresenter(v: PenerimaanContract.View) : PenerimaanContract.presenter {
    private var view : PenerimaanContract.View? = v
    private var apiService = APIClient.APIService()

    override fun getLogistikProduk(token: String) {
        val request = apiService.getPenerimaan(token)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Penerimaan>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Penerimaan>>,
                response: Response<WrappedListResponse<Penerimaan>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.isNotEmpty()){
                          view?.attachToRecycler(body.data)
                        }else{
                            view?.emptyData()
                        }
                    }
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<Penerimaan>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }
}