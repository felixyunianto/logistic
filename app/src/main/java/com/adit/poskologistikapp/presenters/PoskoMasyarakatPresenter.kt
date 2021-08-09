package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.PoskoActivityContract
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PoskoMasyarakatPresenter(v : PoskoActivityContract.PoskoMasyarakatView?) : PoskoActivityContract.PoskoMasyarakatPresenter  {
    private var view : PoskoActivityContract.PoskoMasyarakatView? = v
    private var apiServices = APIClient.APIService()
    override fun infoPosko() {
        val request = apiServices.infoPosko()
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Posko>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Posko>>,
                response: Response<WrappedListResponse<Posko>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.attachToRecycler(body.data)
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
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }


}