package com.adit.poskologistikapp.presenters

import android.content.Context
import com.adit.poskologistikapp.contracts.LoginActivityContract
import com.adit.poskologistikapp.models.User
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import com.adit.poskologistikapp.utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityPresenter(v: LoginActivityContract.LoginActivityView?) : LoginActivityContract.LoginActivityPresenter {
    private var view : LoginActivityContract.LoginActivityView? = v
    private var apiServices = APIClient.APIService()

    override fun login(email: String, password: String, context: Context) {
        val request = apiServices.login(email, password)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<User>>{
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null && body.status == 200){
                        Constants.setToken(context, "Bearer "+ body.data.token)
                        Constants.setLevel(context, body.data.level)
                        view?.showToast("Selamat datang ${body.data.username}")
                        view?.successLogin(body.data)
                    }
                }else{
                    view?.showToast("Terjadi kesalahan, silahkan coba lagi lain waktu")
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })
    }

    override fun saveDeviceToken(token: String, device_token: String) {
        val request = apiServices.saveDeviceToken(token, device_token)
        request.enqueue(object :  Callback<WrappedResponse<String>> {
            override fun onResponse(
                call: Call<WrappedResponse<String>>,
                response: Response<WrappedResponse<String>>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    if(body != null && body.status == 200){
                        view?.showToast("Device token berhasil disimpan")
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponse<String>>, t: Throwable) {
                view?.showToast("Tidak bisa koneksi ke server")
                view?.hideLoading()
            }

        })

    }

    override fun destroy() {
        view = null
    }
}