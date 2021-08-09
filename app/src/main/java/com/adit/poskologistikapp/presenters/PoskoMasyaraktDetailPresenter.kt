package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.PoskoActivityContract
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.models.LogistikMasuk
import com.adit.poskologistikapp.models.Penerimaan
import com.adit.poskologistikapp.models.Penyaluran
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PoskoMasyaraktDetailPresenter(v : PoskoActivityContract.PoskoMasyarakatDetailView) : PoskoActivityContract.PoskoMasyarakatDetailPresenter{
    private var view : PoskoActivityContract.PoskoMasyarakatDetailView? = v
    private var apiServices = APIClient.APIService()

    override fun masuk(token: String) {
        val request = apiServices.getLogistikMasuk(token)
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
                            view?.attachToRecylerMasuk(body.data)
                            println("MASUK " + body.data)
                        }
                    }
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<LogistikMasuk>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun keluar(id_posko: String) {
        val request = apiServices.getLogistikKeluarByPosko(id_posko)
        view?.showLoading()
        request.enqueue(object :Callback<WrappedListResponse<LogistikKeluar>>{
            override fun onResponse(
                call: Call<WrappedListResponse<LogistikKeluar>>,
                response: Response<WrappedListResponse<LogistikKeluar>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.isNotEmpty()){
                            view?.attachToRecyclerKeluar(body.data)
                            println("KELUAR " + body.data)
                        }
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

    override fun penerimaan(id_posko: String) {
        val request = apiServices.getPenerimaanByPosko(id_posko)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Penerimaan>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Penerimaan>>,
                response: Response<WrappedListResponse<Penerimaan>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.isNotEmpty()){
                            view?.attachToRecylerPenerimaan(body.data)
                            println("TERIMA " + body.data)
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

    override fun penyaluran(id_posko: String) {
        val request = apiServices.getPenyaluranByPosko(id_posko)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Penyaluran>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Penyaluran>>,
                response: Response<WrappedListResponse<Penyaluran>>
            ) {
                val body = response.body()
                if(body != null){
                    if(body.data.isNotEmpty()){
                        view?.attachToRecyclerPenyaluran(body.data)
                        println("SALURAN " + body.data)
                    }
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Penyaluran>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }
}