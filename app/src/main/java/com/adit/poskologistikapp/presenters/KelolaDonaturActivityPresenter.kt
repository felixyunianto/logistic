package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.DonaturActivityContract
import com.adit.poskologistikapp.models.Donatur
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaDonaturActivityPresenter(v: DonaturActivityContract.CreateOrUpdateView?) : DonaturActivityContract.CreateOrUpdateInteraction {
    private var view : DonaturActivityContract.CreateOrUpdateView? = v
    private var apiServices = APIClient.APIService()

    override fun create(
        token: String,
        nama: String,
        jenis_kebutuhan: String,
        keterangan: String,
        alamat: String,
        id_posko: String,
        tanggal: String,
        jumlah: String,
        satuan: String
    ) {
        val request = apiServices.postDonatur(token, nama, jenis_kebutuhan, keterangan, alamat, id_posko, tanggal, jumlah, satuan)
        request.enqueue(object : Callback<WrappedResponse<Donatur>> {
            override fun onResponse(
                call: Call<WrappedResponse<Donatur>>,
                response: Response<WrappedResponse<Donatur>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast(body.message!!)
                        view?.success()
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Donatur>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun update(
        token: String,
        id: String,
        nama: String,
        jenis_kebutuhan: String,
        keterangan: String,
        alamat: String,
        id_posko: String,
        tanggal: String,
        jumlah: String,
        satuan: String
    ) {
        val request = apiServices.editDonatur(token, id, nama, jenis_kebutuhan, keterangan, alamat, id_posko, tanggal, jumlah, satuan)
        request.enqueue(object : Callback<WrappedResponse<Donatur>>{
            override fun onResponse(
                call: Call<WrappedResponse<Donatur>>,
                response: Response<WrappedResponse<Donatur>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast(body.message!!)
                        view?.success()
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Donatur>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun getPosko() {
        val request = apiServices.infoPosko()
        request.enqueue(object : Callback<WrappedListResponse<Posko>>{
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

    override fun destroy() {
        view = null
    }
}