package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.LogistikProdukActivityContract
import com.adit.poskologistikapp.contracts.PoskoActivityContract
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaPoskoPresenter(v : PoskoActivityContract.CreateOrUpdateView?) : PoskoActivityContract.CreateOrUpdateInteraction {
    private var view : PoskoActivityContract.CreateOrUpdateView? = v
    private var apiServices = APIClient.APIService()

    override fun create(
        token: String,
        nama: String,
        jumlah_pengungsi: String,
        kontak_hp: String,
        lokasi: String,
        longitude: String,
        latitude: String
    ) {
        val request = apiServices.postPosko(token, nama, jumlah_pengungsi, kontak_hp, lokasi, latitude, longitude)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<Posko>> {
            override fun onResponse(
                call: Call<WrappedResponse<Posko>>,
                response: Response<WrappedResponse<Posko>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.hideLoading()
                        view?.success()
                    }else{
                        view?.showToast("Terjadi kesalahan")
                        view?.hideLoading()
                    }
                }else{
                    var errorBody = JSONObject(response.errorBody()?.string())
                    view?.showToast(errorBody.getString("error"))
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Posko>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
            }

        })
    }

    override fun update(
        token: String,
        id: String,
        nama: String,
        jumlah_pengungsi: String,
        kontak_hp: String,
        lokasi: String,
        longitude: String,
        latitude: String
    ) {
        val request  = apiServices.editPosko(token, id.toInt(), nama, jumlah_pengungsi, kontak_hp, lokasi, latitude, longitude)
        request.enqueue(object : Callback<WrappedResponse<Posko>>{
            override fun onResponse(
                call: Call<WrappedResponse<Posko>>,
                response: Response<WrappedResponse<Posko>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        view?.hideLoading()
                        view?.success()
                    }else{
                        view?.showToast("Terjadi kesalahan")
                        view?.hideLoading()
                    }
                }else{
                    view?.showToast(response.message())
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Posko>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
            }

        })
    }

    override fun destroy() {
        view = null

    }
}