package com.adit.poskologistikapp.presenters

import com.adit.poskologistikapp.contracts.PenyaluranContract
import com.adit.poskologistikapp.models.Penyaluran
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PenyaluranPresenter(v : PenyaluranContract.View) : PenyaluranContract.presenter {
    private var view : PenyaluranContract.View? = v
    private var apiService = APIClient.APIService()
    override fun getLogistikProduk(token: String) {
        val request = apiService.getPenyaluran(token)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedListResponse<Penyaluran>>{
            override fun onResponse(
                call: Call<WrappedListResponse<Penyaluran>>,
                response: Response<WrappedListResponse<Penyaluran>>
            ) {
                if(response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        if (body.data.isNotEmpty()) {
                            view?.attachToRecycler(body.data)
                        } else {
                            view?.emptyData()

                        }
                    }
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedListResponse<Penyaluran>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
                println(t.message)
            }

        })
    }

    override fun delete(token: String, id: String) {
        val request = apiService.deletePenyaluran(token, id)
        request.enqueue(object : Callback<WrappedResponse<Penyaluran>>{
            override fun onResponse(
                call: Call<WrappedResponse<Penyaluran>>,
                response: Response<WrappedResponse<Penyaluran>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null){
                        view?.showToast(body.message)
                        getLogistikProduk(token)
                    }
                }else{
                    val errorJson = JSONObject(response.errorBody()?.string())
                    view?.showToast(errorJson.getString("error"))
                }

                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Penyaluran>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }


}