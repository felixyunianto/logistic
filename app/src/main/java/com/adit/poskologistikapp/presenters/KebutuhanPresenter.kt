package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.KebutuhanActivityContract
import com.adit.poskologistikapp.models.Kebutuhan
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KebutuhanPresenter(v : KebutuhanActivityContract.KebutuhanLogistikActivityView?) : KebutuhanActivityContract.KebutuhanLogistikPresenter  {
    private var view : KebutuhanActivityContract.KebutuhanLogistikActivityView? = v
    private var apiService = APIClient.APIService()
    override fun infoKebutuhanLogistik(token: String, id_posko: String) {
        val request = apiService.getKebutuhanByPosko(token, id_posko)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Kebutuhan>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Kebutuhan>>,
                response: Response<WrappedListResponse<Kebutuhan>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        if(body.data.isNotEmpty()){
                            view?.attachKebutuhanLogistikRecycler(body.data)
                            view?.hideLoading()
                        }else{
                            view?.emptyData()
                            view?.hideLoading()
                        }
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }

            }

            override fun onFailure(call: Call<WrappedListResponse<Kebutuhan>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun delete(token: String, id: String) {
        val request = apiService.deleteKebutuhan(token, id)
        request.enqueue(object : Callback<WrappedResponse<Kebutuhan>>{
            override fun onResponse(
                call: Call<WrappedResponse<Kebutuhan>>,
                response: Response<WrappedResponse<Kebutuhan>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        infoKebutuhanLogistik(token, id)
                    }else{
                        view?.showToast(body?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Kebutuhan>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }


}