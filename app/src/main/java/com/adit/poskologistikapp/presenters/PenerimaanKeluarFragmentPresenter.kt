package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.PenerimaanKeluarFragmentContract
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.models.Penerimaan
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenerimaanKeluarFragmentPresenter(v : PenerimaanKeluarFragmentContract.PenerimaanFragmentView) : PenerimaanKeluarFragmentContract.PenerimaanFragmentPresenter {
    private var view : PenerimaanKeluarFragmentContract.PenerimaanFragmentView? =v
    private var apiService = APIClient.APIService()
    override fun infoPenerimaanKeluar(token: String) {
        val request = apiService.getLogistikKeluarPenerima(token)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<LogistikKeluar>> {
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
                    }
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<LogistikKeluar>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke serve")
                view?.hideLoading()
            }

        })
    }

    override fun konfirmasi(token: String,id : String) {
        val request = apiService.tambahPenerimaan(token,id)
        request.enqueue(object : Callback<WrappedResponse<String>>{
            override fun onResponse(
                call: Call<WrappedResponse<String>>,
                response: Response<WrappedResponse<String>>
            ) {
                println("RESPONSE " + response)
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?. showToast("Berhasil")
                        infoPenerimaanKeluar(token)
                    }
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<String>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println("MESSAGE"+t.message)
            }

        })
    }

    override fun destroy() {
        view = null
    }


}