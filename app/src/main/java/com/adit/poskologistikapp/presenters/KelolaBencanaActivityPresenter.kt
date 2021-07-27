package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.BencanaActivityContract
import com.adit.poskologistikapp.models.Bencana
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaBencanaActivityPresenter(v : BencanaActivityContract.CreateOrUpdateView?) : BencanaActivityContract.CreateOrUpdatePresenter {
    private var view : BencanaActivityContract.CreateOrUpdateView? = v
    private var apiService = APIClient.APIService()

    override fun create(
        token: String,
        name: RequestBody,
        foto: MultipartBody.Part,
        detail: RequestBody,
        date: RequestBody
    ) {
        val request = apiService.postBencana(token, name, foto, detail, date)
        request.enqueue(object : Callback<WrappedResponse<Bencana>> {
            override fun onResponse(
                call: Call<WrappedResponse<Bencana>>,
                response: Response<WrappedResponse<Bencana>>
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
                    println("RESPONSE " + response)
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Bencana>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun update(
        token: String,
        id: String,
        name: RequestBody,
        foto: MultipartBody.Part,
        detail: RequestBody,
        date: RequestBody,
        method: RequestBody
    ) {
        val request = apiService.editBencana(token, id.toInt(), name, foto, detail, date, method)
        request.enqueue(object : Callback<WrappedResponse<Bencana>> {
            override fun onResponse(
                call: Call<WrappedResponse<Bencana>>,
                response: Response<WrappedResponse<Bencana>>
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

            override fun onFailure(call: Call<WrappedResponse<Bencana>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun updateTanpaFoto(
        token: String,
        id: String,
        name: RequestBody,
        detail: RequestBody,
        date: RequestBody,
        method: RequestBody
    ) {
        val request = apiService.editBencanaTanpaFoto(token, id.toInt(), name, detail, date, method)
        request.enqueue(object : Callback<WrappedResponse<Bencana>> {
            override fun onResponse(
                call: Call<WrappedResponse<Bencana>>,
                response: Response<WrappedResponse<Bencana>>
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

            override fun onFailure(call: Call<WrappedResponse<Bencana>>, t: Throwable) {
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