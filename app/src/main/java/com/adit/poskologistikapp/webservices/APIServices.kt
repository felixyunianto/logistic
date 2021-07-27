package com.adit.poskologistikapp.webservices

import com.adit.poskologistikapp.models.*
import com.adit.poskologistikapp.responses.WrappedListResponse
import com.adit.poskologistikapp.responses.WrappedResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface APIServices {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<WrappedResponse<User>>

    @FormUrlEncoded
    @POST("save-token")
    fun saveDeviceToken(
        @Header("Authorization") token: String,
        @Field("device_token") device_token: String,
    ): Call<WrappedResponse<String>>

    //Bencana
    @GET("bencana")
    fun infoBencana(): Call<WrappedListResponse<Bencana>>

    @Multipart
    @POST("bencana")
    fun postBencana(
        @Header("Authorization") token: String,
        @Part("nama") nama: RequestBody,
        @Part foto: MultipartBody.Part,
        @Part("detail") detail: RequestBody,
        @Part("tanggal") tanggal: RequestBody,
    ): Call<WrappedResponse<Bencana>>

    @Multipart
    @POST("bencana/{id}")
    fun editBencana(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("nama") nama: RequestBody,
        @Part foto: MultipartBody.Part,
        @Part("detail") detail: RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part("_method") method: RequestBody
    ): Call<WrappedResponse<Bencana>>

    @Multipart
    @POST("bencana/{id}")
    fun editBencanaTanpaFoto(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part("nama") nama: RequestBody,
        @Part("detail") detail: RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part("_method") method: RequestBody
    ): Call<WrappedResponse<Bencana>>


    @DELETE("bencana/{id}")
    fun deleteBencana(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Call<WrappedResponse<Bencana>>

    //Posko
    @GET("posko")
    fun infoPosko(): Call<WrappedListResponse<Posko>>

    @FormUrlEncoded
    @POST("posko")
    fun postPosko(
        @Header("Authorization") token: String,
        @Field("nama") name: String,
        @Field("jumlah_pengungsi") jumlah_pengungsi : String,
        @Field("kontak_hp") kontak_hp : String,
        @Field("lokasi") lokasi : String,
        @Field("latitude") latitude : String,
        @Field("longitude") longitude : String,

        ): Call<WrappedResponse<Posko>>

    @FormUrlEncoded
    @PUT("posko/{id}")
    fun editPosko(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("nama") name: String,
        @Field("jumlah_pengungsi") jumlah_pengungsi : String,
        @Field("kontak_hp") kontak_hp : String,
        @Field("lokasi") lokasi : String,
        @Field("latitude") latitude : String,
        @Field("longitude") longitude : String,
    ): Call<WrappedResponse<Posko>>

    @DELETE("posko/{id}")
    fun deletePosko(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Call<WrappedResponse<Posko>>

    //Petugas Posko
    @GET("petugas-posko")
    fun getPetugas(
        @Header("Authorization") token: String,
    ): Call<WrappedListResponse<Petugas>>

    @FormUrlEncoded
    @POST("petugas-posko")
    fun createPetugasPosko(
        @Header("Authorization") token: String?,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("password_confrim") password_confrim: String,
        @Field("level") level: String,
        @Field("id_posko") id_posko: String,
    ): Call<WrappedResponse<Petugas>>

    @FormUrlEncoded
    @PUT("petugas-posko/{id}")
    fun updatePetugasPosko(
        @Header("Authorization") token: String?,
        @Path("id") id: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("password_confrim") password_confrim: String,
        @Field("level") level: String,
        @Field("id_posko") id_posko: String,
    ): Call<WrappedResponse<Petugas>>

    @DELETE("petugas-posko/{id}")
    fun deletePetugasPosko(
        @Header("Authorization") token: String?,
        @Path("id") id: String,
    ): Call<WrappedResponse<Petugas>>

    //Donatur
    @GET("donatur")
    fun getDonatur(): Call<WrappedListResponse<Donatur>>
    @FormUrlEncoded
    @POST("donatur")
    fun postDonatur(
        @Header("Authorization") token: String?,
        @Field("nama") nama : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("alamat") alamat : String,
        @Field("id_posko") id_posko : String,
        @Field("tanggal") tanggal : String,
        @Field("jumlah") jumlah : String,
        @Field("satuan") satuan : String,
    ) : Call<WrappedResponse<Donatur>>

    @FormUrlEncoded
    @PUT("donatur/{id}")
    fun editDonatur(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
        @Field("nama") nama : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("alamat") alamat : String,
        @Field("id_posko") id_posko : String,
        @Field("tanggal") tanggal : String,
        @Field("jumlah") jumlah : String,
        @Field("satuan") satuan : String,
    ) : Call<WrappedResponse<Donatur>>

    @DELETE("donatur/{id}")
    fun hapusDonatur(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
    ) : Call<WrappedResponse<Donatur>>

    //Logistik Produk
    @GET("logistik-produk")
    fun getLogistikProduk(
        @Header("Authorization") token : String,
    ) : Call<WrappedListResponse<Logistik>>

    @FormUrlEncoded
    @POST("logistik-produk")
    fun postLogistikProduk(
        @Header("Authorization") token : String,
        @Field("nama_produk") nama_produk : String,
        @Field("jumlah") jumlah : String,
        @Field("satuan") satuan : String,
    ) : Call<WrappedResponse<Logistik>>

    @FormUrlEncoded
    @PUT("logistik-produk/{id}")
    fun putLogistikProduk(
        @Header("Authorization") token : String,
        @Path("id") id : String,
        @Field("nama_produk") nama_produk : String,
        @Field("jumlah") jumlah : String,
        @Field("satuan") satuan : String,
    ) : Call<WrappedResponse<Logistik>>

    @DELETE("logistik-produk/{id}")
    fun deleteLogistikProduk(
        @Header("Authorization") token : String,
        @Path("id") id : String,
    ) : Call<WrappedResponse<Logistik>>

    //Logistik Masuk
    @GET("logistik-masuk")
    fun getLogistikMasuk(
        @Header("Authorization") token : String,
    ) : Call<WrappedListResponse<LogistikMasuk>>
    
    @Multipart
    @POST("logistik-masuk")
    fun postLogistikMasukLama(
        @Header("Authorization") token : String,
        @Part("jenis_kebutuhan") jenis_kebutuhan : RequestBody,
        @Part("keterangan") keterangan : RequestBody,
        @Part("jumlah") jumlah : RequestBody,
        @Part("pengirim") pengirim : RequestBody,
        @Part("satuan") satuan : RequestBody,
        @Part("status") status : RequestBody,
        @Part("tanggal") tanggal : RequestBody,
        @Part foto : MultipartBody.Part,
        @Part("id_produk") id_produk : RequestBody
    ) : Call<WrappedResponse<LogistikMasuk>>

    @Multipart
    @POST("logistik-masuk")
    fun postLogistikMasukBaru(
        @Header("Authorization") token : String,
        @Part("jenis_kebutuhan") jenis_kebutuhan : RequestBody,
        @Part("keterangan") keterangan : RequestBody,
        @Part("jumlah") jumlah : RequestBody,
        @Part("pengirim") pengirim : RequestBody,
        @Part("satuan") satuan : RequestBody,
        @Part("status") status : RequestBody,
        @Part("tanggal") tanggal : RequestBody,
        @Part foto : MultipartBody.Part,
        @Part("baru") baru : RequestBody,
        @Part("nama_produk") nama_produk : RequestBody,
    ) : Call<WrappedResponse<LogistikMasuk>>

    @DELETE("logistik-masuk/{id}")
    fun deleteLogistikMasuk(
        @Header("Authorization") token : String,
        @Path("id") id : String,
    ) : Call<WrappedResponse<LogistikMasuk>>

    @GET("logistik-keluar")
    fun getLogistikKeluar(
        @Header("Authorization") token :String,
    ) : Call<WrappedListResponse<LogistikKeluar>>

    @GET("logistik-keluar/penerimaan")
    fun getLogistikKeluarPenerima(
        @Header("Authorization") token :String,
    ) : Call<WrappedListResponse<LogistikKeluar>>

    @FormUrlEncoded
    @POST("logistik-keluar")
    fun postLogistikKeluar(
        @Header("Authorization") token : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("jumlah") jumlah : String,
        @Field("status") status : String,
        @Field("satuan") satuan : String,
        @Field("tanggal") tanggal : String,
        @Field("id_produk") id_produk : String,
        @Field("penerima_id") penerima_id : String,
    ) : Call<WrappedResponse<LogistikKeluar>>

    @FormUrlEncoded
    @PUT("logistik-keluar/{id}")
    fun editLogistikKeluar(
        @Header("Authorization") token : String,
        @Path("id") id : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("jumlah") jumlah : String,
        @Field("status") status : String,
        @Field("satuan") satuan : String,
        @Field("tanggal") tanggal : String,
        @Field("id_produk") id_produk : String,
        @Field("penerima_id") penerima_id : String,
    ) : Call<WrappedResponse<LogistikKeluar>>

    @DELETE("logistik-keluar/{id}")
    fun hapusLogistikKeluar(
        @Header("Authorization") token : String,
        @Path("id") id : String,
    ) : Call<WrappedResponse<LogistikKeluar>>

    @GET("penerimaan")
    fun getPenerimaan(
        @Header("Authorization") token : String,
    ) : Call<WrappedListResponse<Penerimaan>>

    @POST("penerimaan/keluar/{id}")
    fun tambahPenerimaan(
        @Header("Authorization") token : String,
        @Path("id") id : String
    ) : Call<WrappedResponse<String>>

    //Penyaluran
    @GET("penyaluran")
    fun getPenyaluran(
        @Header("Authorization") token : String,
    ) : Call<WrappedListResponse<Penyaluran>>

    @FormUrlEncoded
    @POST("penyaluran")
    fun postPenyaluran(
        @Header("Authorization") token : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("jumlah") jumlah : String,
        @Field("status") status : String,
        @Field("satuan") satuan : String,
        @Field("tanggal") tanggal : String,
        @Field("id_produk") id_produk : String,
        @Field("penerima") penerima : String,
    ) : Call<WrappedResponse<Penyaluran>>

    @GET("kebutuhan-logistik")
    fun getKebutuhan() : Call<WrappedListResponse<Kebutuhan>>

    @GET("kebutuhan-logistik/posko")
    fun getKebutuhanByPosko(
        @Header("Authorization") token: String?,
    ) : Call<WrappedListResponse<Kebutuhan>>

    @FormUrlEncoded
    @POST("kebutuhan-logistik")
    fun postKebutuhan(
        @Header("Authorization") token: String?,
        @Field("id_produk") id_produk : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("jumlah") jumlah : String,
        @Field("tanggal") tanggal : String,
        @Field("satuan") satuan : String,
    ) : Call<WrappedResponse<Kebutuhan>>

    @FormUrlEncoded
    @PUT("kebutuhan-logistik/{id}")
    fun putKebutuhan(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
        @Field("id_produk") id_produk : String,
        @Field("jenis_kebutuhan") jenis_kebutuhan : String,
        @Field("keterangan") keterangan : String,
        @Field("jumlah") jumlah : String,
        @Field("tanggal") tanggal : String,
        @Field("satuan") satuan : String,
    ) : Call<WrappedResponse<Kebutuhan>>

    @DELETE("kebutuhan-logistik/{id}")
    fun deleteKebutuhan(
        @Header("Authorization") token: String?,
        @Path("id") id : String,
    ) : Call<WrappedResponse<Kebutuhan>>
}