package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.PetugasActivityContract
import com.adit.poskologistikapp.models.Petugas
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaPetugasActivityPresenter(v : PetugasActivityContract.CreateOrUpdateView?) : PetugasActivityContract.CreateOrUpdatePresenter {
    private var view : PetugasActivityContract.CreateOrUpdateView? = v
    private var apiService = APIClient.APIService()

    override fun create(
        token: String,
        username: String,
        password: String,
        confirm_password: String,
        level: String,
        id_posko: String
    ) {
        val request = apiService.createPetugasPosko(token, username, password, confirm_password, level, id_posko)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<Petugas>>{
            override fun onResponse(
                call: Call<WrappedResponse<Petugas>>,
                response: Response<WrappedResponse<Petugas>>
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

            override fun onFailure(call: Call<WrappedResponse<Petugas>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }
        })
    }

    override fun update(
        token: String,
        id: String,
        username: String,
        password: String,
        confirm_password: String,
        level: String,
        id_posko: String
    ) {
        val request = apiService.updatePetugasPosko(token, id, username, password, confirm_password, level, id_posko)
        view?.showLoading()
        request.enqueue(object: Callback<WrappedResponse<Petugas>>{
            override fun onResponse(
                call: Call<WrappedResponse<Petugas>>,
                response: Response<WrappedResponse<Petugas>>
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

            override fun onFailure(call: Call<WrappedResponse<Petugas>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }
        })
    }

    override fun getPosko() {
        val request = apiService.infoPosko()
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