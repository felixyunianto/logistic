package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.LogistikMasukActivityContract
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.LogistikMasuk
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaLogistikMasukActivityPresenter(v : LogistikMasukActivityContract.CreateOrUpdateView?) : LogistikMasukActivityContract.CreateOrUpdatePresenter{
    private var view : LogistikMasukActivityContract.CreateOrUpdateView? = v
    private var apiService = APIClient.APIService()

    override fun create(
        token: String,
        jenis_kebutuhan: RequestBody,
        keterangan: RequestBody,
        jumlah: RequestBody,
        pengirim: RequestBody,
        satuan: RequestBody,
        status: RequestBody,
        tanggal: RequestBody,
        foto: MultipartBody.Part,
        id_produk: RequestBody
    ) {
        val request = apiService.postLogistikMasukLama(token, jenis_kebutuhan, keterangan, jumlah, pengirim, satuan, status, tanggal, foto, id_produk)
        request.enqueue(object : Callback<WrappedResponse<LogistikMasuk>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikMasuk>>,
                response: Response<WrappedResponse<LogistikMasuk>>
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

            override fun onFailure(call: Call<WrappedResponse<LogistikMasuk>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }
        })
    }

    override fun createNew(
        token: String,
        jenis_kebutuhan: RequestBody,
        keterangan: RequestBody,
        jumlah: RequestBody,
        pengirim: RequestBody,
        satuan: RequestBody,
        status: RequestBody,
        tanggal: RequestBody,
        foto: MultipartBody.Part,
        baru: RequestBody,
        nama_produk: RequestBody,
    ) {
        val request = apiService.postLogistikMasukBaru(token, jenis_kebutuhan, keterangan, jumlah, pengirim, satuan, status, tanggal, foto, baru, nama_produk)
        request.enqueue(object : Callback<WrappedResponse<LogistikMasuk>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikMasuk>>,
                response: Response<WrappedResponse<LogistikMasuk>>
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

            override fun onFailure(call: Call<WrappedResponse<LogistikMasuk>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }
        })
    }

    override fun update(
        token: String,
        id: String,
        jenis_kebutuhan: RequestBody,
        keterangan: RequestBody,
        jumlah: RequestBody,
        pengirim: RequestBody,
        satuan: RequestBody,
        status: RequestBody,
        tanggal: RequestBody,
        foto: MultipartBody.Part,
        id_produk: RequestBody,
        _method: RequestBody
    ) {
        val request = apiService.editLogistikMasuk(token, id, jenis_kebutuhan, keterangan, jumlah, pengirim, satuan, status, tanggal, foto, id_produk, _method)
        request.enqueue(object : Callback<WrappedResponse<LogistikMasuk>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikMasuk>>,
                response: Response<WrappedResponse<LogistikMasuk>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.success()
                    }
                }else{
                    var errorJson = JSONObject(response.errorBody()?.string())
                    view?.showToast(errorJson.getString("error"))

                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikMasuk>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun updateTanpaFoto(
        token: String,
        id: String,
        jenis_kebutuhan: RequestBody,
        keterangan: RequestBody,
        jumlah: RequestBody,
        pengirim: RequestBody,
        satuan: RequestBody,
        status: RequestBody,
        tanggal: RequestBody,
        id_produk: RequestBody,
        _method: RequestBody
    ) {
        val request = apiService.editLogistikMasukTanpaFoto(token, id, jenis_kebutuhan, keterangan, jumlah, pengirim, satuan, status, tanggal, id_produk, _method)
        request.enqueue(object : Callback<WrappedResponse<LogistikMasuk>>{
            override fun onResponse(
                call: Call<WrappedResponse<LogistikMasuk>>,
                response: Response<WrappedResponse<LogistikMasuk>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.success()
                    }
                }else{
                    var errorJson = JSONObject(response.errorBody()?.string())
                    view?.showToast(errorJson.getString("error"))

                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<LogistikMasuk>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun getLogistikProduk(token: String) {
        val request = apiService.getLogistikProduk(token)
        request.enqueue(object : Callback<WrappedListResponse<Logistik>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Logistik>>,
                response: Response<WrappedListResponse<Logistik>>
            ) {
                if(response.isSuccessful){
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

            override fun onFailure(call: Call<WrappedListResponse<Logistik>>, t: Throwable) {
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