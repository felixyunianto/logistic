package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.LogistikProdukActivityContract
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaLogistikProdukActivityPresenter(v : LogistikProdukActivityContract.CreateOrUpdateView?) : LogistikProdukActivityContract.CreateOrUpdatePresenter {
    private var view : LogistikProdukActivityContract.CreateOrUpdateView? = v
    private var apiServices = APIClient.APIService()

    override fun create(token: String, nama_produk: String, jumlah: String, satuan: String) {
        val request = apiServices.postLogistikProduk(token, nama_produk, jumlah, satuan)
        request.enqueue(object : Callback<WrappedResponse<Logistik>>{
            override fun onResponse(
                call: Call<WrappedResponse<Logistik>>,
                response: Response<WrappedResponse<Logistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.success()
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Logistik>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun update(
        token: String,
        id: String,
        nama_produk: String,
        jumlah: String,
        satuan: String
    ) {
        val request = apiServices.putLogistikProduk(token, id, nama_produk, jumlah, satuan)
        request.enqueue(object : Callback<WrappedResponse<Logistik>>{
            override fun onResponse(
                call: Call<WrappedResponse<Logistik>>,
                response: Response<WrappedResponse<Logistik>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.success()
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Logistik>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy(token: String, id: String) {
        view = null
    }
}