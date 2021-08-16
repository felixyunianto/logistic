package com.adit.poskologistikapp.utilities

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.adit.poskologistikapp.webservices.APIServices
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    companion object {
        private var retrofit: Retrofit? = null
        private var okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()

        fun APIService(): APIServices = getClient().create(APIServices::class.java)

        private fun getClient(): Retrofit {
            return if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(Constants.API_ENDPOINT).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            } else {
                retrofit!!
            }
        }
    }
}

class Constants {
    companion object {
        const val API_ENDPOINT = "https://logistik-brebes-adit.herokuapp.com/api/"

        fun getToken(context: Context): String {
            val pref = context.getSharedPreferences("TOKEN", MODE_PRIVATE)
            val token = pref?.getString("TOKEN", "UNDEFINED")
            return token!!
        }

        fun setToken(context: Context, token: String) {
            val pref = context.getSharedPreferences("TOKEN", MODE_PRIVATE)
            pref.edit().apply {
                putString("TOKEN", token)
                apply()
            }
        }

        fun setList(context: Context, value : String){
            val pref = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("USERDATA", value)
                apply()
            }
        }

        fun getList(context: Context) : String?{
            val list = context.getSharedPreferences("USERDATA", Context.MODE_PRIVATE)
            return list?.getString("USERDATA", null)
        }

        fun clearList(context: Context){
            val pref = context.getSharedPreferences("USERDATA", MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun getLevel(context: Context): String{
            val pref = context.getSharedPreferences("LEVEL", MODE_PRIVATE)
            val level = pref?.getString("LEVEL", "UNDEFINED")
            return level!!
        }

        fun setLevel(context: Context, level : String){
            val pref = context.getSharedPreferences("LEVEL", MODE_PRIVATE)
            pref.edit().apply{
                putString("LEVEL", level)
                apply()
            }
        }

        fun clearToken(context: Context) {
            val pref = context.getSharedPreferences("TOKEN", MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun getDeviceToken(context: Context) : String{
            val pref = context.getSharedPreferences("DEVICE_TOKEN", MODE_PRIVATE)
            val deviceToken = pref?.getString("DEVICE_TOKEN", "UNDEFINED")
            return deviceToken!!
        }

        fun setDeviceToken(context: Context, device_token : String){
            val pref = context.getSharedPreferences("DEVICE_TOKEN", MODE_PRIVATE)
            pref.edit().apply {
                putString("DEVICE_TOKEN", device_token)
                apply()
            }
        }
    }
}