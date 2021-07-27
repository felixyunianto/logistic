package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.KebutuhanPemberitahuanContract
import com.adit.poskologistikapp.models.Bencana
import com.adit.poskologistikapp.models.Kebutuhan
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KebutuhanPemberitahuanPresenter(v : KebutuhanPemberitahuanContract.KebutuhanActivityView) : KebutuhanPemberitahuanContract.KebutuhanActivityPresenter {
    private var view : KebutuhanPemberitahuanContract.KebutuhanActivityView? = v
    private var apiServices = APIClient.APIService()
    override fun infoBencana() {
        val request = apiServices.getKebutuhan()
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Kebutuhan>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Kebutuhan>>,
                response: Response<WrappedListResponse<Kebutuhan>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.isNotEmpty()){
                            view?.attachToRecycle(body.data)
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

            override fun onFailure(call: Call<WrappedListResponse<Kebutuhan>>, t: Throwable) {
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