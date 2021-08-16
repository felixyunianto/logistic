package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.LogistikKeluarActivityContract
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaLogistikKeluarActivityPresenter(v : LogistikKeluarActivityContract.CreateOrUpdateView?) : LogistikKeluarActivityContract.CreateOrUpdatePresenter {
    private var view : LogistikKeluarActivityContract.CreateOrUpdateView? = v
    private var apiServices = APIClient.APIService()
    override fun getProduk(token: String) {
        val request = apiServices.getLogistikProduk(token)
        request.enqueue(object : Callback<WrappedListResponse<Logistik>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Logistik>>,
                response: Response<WrappedListResponse<Logistik>>
            ) {
                println("RESPONSE CODE " + response)
                if (response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.attachToSpinnerProduk(body.data)
                    }else{
                        view?.showToast(body?.message!!)
                    }
                    view?.showToast(body?.message!!)
                }else{
                    var errorBody = JSONObject(response.errorBody()?.string())
                    view?.showToast(errorBody.getString("error"))
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Logistik>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun getPosko() {
        val request = apiServices.infoPosko()
        request.enqueue(object : Callback<WrappedListResponse<Posko>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Posko>>,
                response: Response<WrappedListResponse<Posko>>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.attachToSpinner(body.data)
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Posko>>, t: Throwable) {
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
        penerima_id: String
    ) {
        val request = apiServices.postLogistikKeluar(token, jenis_kebutuhan, keterangan, jumlah, status, satuan, tanggal, id_produk, penerima_id)
        request.enqueue(object : Callback<WrappedResponse<LogistikKeluar>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikKeluar>>,
                response: Response<WrappedResponse<LogistikKeluar>>
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
                    if(response.code() == 409){
                        view?.showToast("Barang tidak cukup")
                    }
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikKeluar>>, t: Throwable) {
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
        penerima_id: String
    ) {
        val request = apiServices.editLogistikKeluar(token, id, jenis_kebutuhan, keterangan, jumlah, status, satuan, tanggal, id_produk, penerima_id)
        request.enqueue(object : Callback<WrappedResponse<LogistikKeluar>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikKeluar>>,
                response: Response<WrappedResponse<LogistikKeluar>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                    }else{
                        view?.showToast(body?.message!!)
                    }
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