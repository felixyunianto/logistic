package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.KebutuhanActivityContract
import com.adit.poskologistikapp.models.Kebutuhan
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaKebutuhanPresenter(v: KebutuhanActivityContract.CreateOrUpdateView) : KebutuhanActivityContract.CreateOrUpdateInteraction {
    private var view : KebutuhanActivityContract.CreateOrUpdateView? = v
    private var apiServices = APIClient.APIService()

    override fun create(
        token: String,
        id_produk: String,
        jenis_kebutuhan: String,
        keterangan: String,
        jumlah: String,
        status: String,
        tanggal: String,
        satuan: String
    ) {
        val request = apiServices.postKebutuhan(token, id_produk, jenis_kebutuhan, keterangan, jumlah, tanggal, satuan)
        request.enqueue(object : Callback<WrappedResponse<Kebutuhan>> {
            override fun onResponse(
                call: Call<WrappedResponse<Kebutuhan>>,
                response: Response<WrappedResponse<Kebutuhan>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.success()
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Kebutuhan>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun update(
        token: String,
        id: String,
        id_produk: String,
        jenis_kebutuhan: String,
        keterangan: String,
        jumlah: String,
        status: String,
        tanggal: String,
        satuan: String
    ) {
        val request = apiServices.putKebutuhan(token, id, id_produk, jenis_kebutuhan, keterangan, jumlah, tanggal, satuan)
        request.enqueue(object : Callback<WrappedResponse<Kebutuhan>>{
            override fun onResponse(
                call: Call<WrappedResponse<Kebutuhan>>,
                response: Response<WrappedResponse<Kebutuhan>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.success()
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Kebutuhan>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }
        })
    }

    override fun getPosko(token: String) {
        val request = apiServices.getLogistikProduk(token)
        request.enqueue(object : Callback<WrappedListResponse<Logistik>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Logistik>>,
                response: Response<WrappedListResponse<Logistik>>
            ) {
                println("RESPONSE CODE " + response)
                if (response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.attachToSpinner(body.data)
                    }else{
                        view?.showToast(body?.message!!)
                    }
                    view?.showToast(body?.message!!)
                }else{

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