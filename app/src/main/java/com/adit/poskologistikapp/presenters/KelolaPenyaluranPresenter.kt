package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.PenyaluranContract
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.models.Penyaluran
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaPenyaluranPresenter(v : PenyaluranContract.CreateOrUpdateView?) : PenyaluranContract.CreateOrUpdatePresenter {

    private var view : PenyaluranContract.CreateOrUpdateView? = v
    private var apiServices = APIClient.APIService()

    override fun getProduk(token : String) {
        val request = apiServices.getLogistikProduk(token)
        request.enqueue(object : Callback<WrappedListResponse<Logistik>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Logistik>>,
                response: Response<WrappedListResponse<Logistik>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.attachToSpinner(body.data)
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    var errorBody = JSONObject(response.errorBody()?.string())
                    view?.showToast(errorBody.getString("message"))
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Logistik>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun create(
        token: String,
        jenis_kebutuhan: String,
        keterangan: String,
        jumlah: String,
        status: String,
        satuan: String,
        tanggal: String,
        id_produk: String,
        penerima: String
    ) {
        val request = apiServices.postPenyaluran(token, jenis_kebutuhan, keterangan, jumlah, status, satuan, tanggal, id_produk, penerima)
        request.enqueue(object : Callback<WrappedResponse<Penyaluran>>{
            override fun onResponse(
                call: Call<WrappedResponse<Penyaluran>>,
                response: Response<WrappedResponse<Penyaluran>>
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
                    var errorBody = JSONObject(response.errorBody()?.string())
                    view?.showToast(errorBody.getString("message"))
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Penyaluran>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun edit(
        token: String,
        id: String,
        jenis_kebutuhan: String,
        keterangan: String,
        jumlah: String,
        status: String,
        satuan: String,
        tanggal: String,
        id_produk: String,
        penerima: String
    ) {
        val request = apiServices.ubahPPenyaluran(token, id, jenis_kebutuhan, keterangan, jumlah, status, satuan, tanggal, id_produk, penerima)
        request.enqueue(object : Callback<WrappedResponse<Penyaluran>>{
            override fun onResponse(
                call: Call<WrappedResponse<Penyaluran>>,
                response: Response<WrappedResponse<Penyaluran>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                      view?.showToast(body.message)
                      view?.success()
                    }

                    view?.hideLoading()
                }else{
                    val errorJson = JSONObject(response.errorBody()?.string())
                    view?.showToast(errorJson.getString("error"))
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Penyaluran>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }
}