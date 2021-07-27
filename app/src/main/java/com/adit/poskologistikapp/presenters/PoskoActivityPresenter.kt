package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.PoskoActivityContract
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PoskoActivityPresenter(v : PoskoActivityContract.PoskoActivityView?) : PoskoActivityContract.PoskoActivityPresenter {
    private var view : PoskoActivityContract.PoskoActivityView? = v
    private var apiServices = APIClient.APIService()

    override fun infoPosko() {
        val request = apiServices.infoPosko()
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Posko>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Posko>>,
                response: Response<WrappedListResponse<Posko>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if (body.data.isNotEmpty()){
                            view?.attachToRecycler(body.data)
                        }else{
                            view?.emptyData()
                        }
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }else{
                    view?.showToast(response.message())
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<Posko>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                println(t.message)
            }
        })
    }

    override fun delete(token: String, id: String) {
        val request = apiServices.deletePosko(token, id.toInt())
        request.enqueue(object : Callback<WrappedResponse<Posko>>{
            override fun onResponse(
                call: Call<WrappedResponse<Posko>>,
                response: Response<WrappedResponse<Posko>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        view?.showToast(body.message)
                        infoPosko()
                    }else{
                        view?.showToast("Terjadi Kesalahan")
                    }
                }else{
                    view?.showToast(response.message())
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