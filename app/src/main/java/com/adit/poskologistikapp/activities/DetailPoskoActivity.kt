package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.adapters.DetailPoskoKeluarAdapter
import com.adit.poskologistikapp.adapters.DetailPoskoMasukAdapter
import com.adit.poskologistikapp.adapters.DetailPoskoPenerimaanAdapter
import com.adit.poskologistikapp.adapters.DetailPoskoPenyaluranAdapter
import com.adit.poskologistikapp.contracts.PoskoActivityContract
import com.adit.poskologistikapp.databinding.ActivityDetailPoskoBinding
import com.adit.poskologistikapp.models.*
import com.adit.poskologistikapp.presenters.PoskoMasyaraktDetailPresenter
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import com.adit.poskologistikapp.utilities.Constants
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPoskoActivity : AppCompatActivity(), PoskoActivityContract.PoskoMasyarakatDetailView {
    private lateinit var binding : ActivityDetailPoskoBinding
    private lateinit var adapterMasuk : DetailPoskoMasukAdapter
    private lateinit var adapterKeluar : DetailPoskoKeluarAdapter
    private lateinit var adapterPenerimaan : DetailPoskoPenerimaanAdapter
    private lateinit var adapterPenyaluran : DetailPoskoPenyaluranAdapter

    private var presenter : PoskoActivityContract.PoskoMasyarakatDetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPoskoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = PoskoMasyaraktDetailPresenter(this)
        parseToView()
        hideShowButton()
        buttonAction()
        doUpdate()
        lihatProdukLogistik()
    }

    private fun hideShowButton(){
        var list = Constants.getList(this)
        var user = Gson().fromJson(list, User::class.java)
        if(getDetailPosko()!!.id == user.id_posko){
            binding.editPengungsi.visibility = View.VISIBLE
        }
    }

    private fun buttonAction(){
        binding.editPengungsi.setOnClickListener {
            binding.etJumlahPengungsi.visibility = View.VISIBLE
            binding.inJumlahPengungsi.visibility = View.VISIBLE
            binding.updatePengungsi.visibility = View.VISIBLE
        }
    }

    private fun doUpdate(){
        binding.updatePengungsi.setOnClickListener {
            val token = Constants.getToken(this)
            val id = getDetailPosko()?.id
            val nama = getDetailPosko()?.nama.toString()
            val jumlahPengungsi = binding.etJumlahPengungsi.text.toString()
            val kontakHp = getDetailPosko()?.kontak_hp.toString()
            val lokasi = getDetailPosko()?.lokasi.toString()
            val latitude = getDetailPosko()?.latitude.toString()
            val longitude = getDetailPosko()?.longitude.toString()

            val apiService = APIClient.APIService()
            val request = apiService.editPosko(token, id!!.toInt(), nama!!, jumlahPengungsi!!, kontakHp!!, lokasi!!, latitude!!, longitude!! )
            request.enqueue(object : Callback<WrappedResponse<Posko>> {
                override fun onResponse(
                    call: Call<WrappedResponse<Posko>>,
                    response: Response<WrappedResponse<Posko>>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            onBackPressed()
                            showToast("Berhasl mengubah jumlah pengungsi")
                        }
                    }
                }

                override fun onFailure(call: Call<WrappedResponse<Posko>>, t: Throwable) {
                    showToast("Tidak bisa koneksi ke server")

                }

            })
        }
    }

    private fun getDetailPosko() : Posko? = intent.getParcelableExtra("POSKO")


    private fun parseToView() {
        binding.tvNamaPosko.text = getDetailPosko()?.nama
        binding.tvLokasi.text = getDetailPosko()?.lokasi
        binding.tvJumlahPengungsi.text = getDetailPosko()?.jumlah_pengungsi
        binding.tvKontakHp.text = getDetailPosko()?.kontak_hp
        binding.etJumlahPengungsi.setText(getDetailPosko()?.jumlah_pengungsi)
    }

    override fun attachToRecylerMasuk(masuk: List<LogistikMasuk>) {
        if(getDetailPosko()!!.id == "1"){
            adapterMasuk = DetailPoskoMasukAdapter(masuk)
            binding.rvMasuk.apply {
                adapter = adapterMasuk
                layoutManager = LinearLayoutManager(this@DetailPoskoActivity)
            }
        }
        println("ID_POSKO "+ getDetailPosko()?.id)
    }

    override fun attachToRecyclerKeluar(keluar: List<LogistikKeluar>) {
        if(getDetailPosko()!!.id == "1"){
            adapterKeluar = DetailPoskoKeluarAdapter(keluar)
            binding.rvKeluar.apply {
                adapter = adapterKeluar
                layoutManager = LinearLayoutManager(this@DetailPoskoActivity)
            }
        }
        println("ID_POSKO "+ getDetailPosko()?.id)
    }

    override fun attachToRecylerPenerimaan(penerimaan: List<Penerimaan>) {
        if(getDetailPosko()!!.id != "1"){
            adapterPenerimaan = DetailPoskoPenerimaanAdapter(penerimaan)
            binding.rvMasuk.apply {
                adapter = adapterPenerimaan
                layoutManager = LinearLayoutManager(this@DetailPoskoActivity)
            }
        }
        println("ID_POSKO "+ getDetailPosko()?.id)
    }

    override fun attachToRecyclerPenyaluran(penyaluran: List<Penyaluran>) {
        if(getDetailPosko()!!.id != "1"){
            adapterPenyaluran = DetailPoskoPenyaluranAdapter(penyaluran)
            binding.rvKeluar.apply {
                adapter = adapterPenyaluran
                layoutManager = LinearLayoutManager(this@DetailPoskoActivity)
            }
        }
        println("ID_POSKO "+ getDetailPosko()?.id)
    }

    override fun showToast(message: String) {
        Toast.makeText(this@DetailPoskoActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loading.isIndeterminate = true
    }

    override fun hideLoading() {
        binding.loading.apply {
            isIndeterminate = true
            progress = 0
            visibility = View.GONE
        }
    }

    private fun lihatProdukLogistik(){
        binding.lihatLogistik.setOnClickListener {
            val intent = Intent(this@DetailPoskoActivity, LogistikProdukActivity::class.java).apply{
                putExtra("IS_FROM_BERANDA", false)
                putExtra("ID_POSKO", getDetailPosko()?.id)
            }

            startActivity(intent)
        }
    }

    private fun getData(){
        val token = Constants.getToken(this@DetailPoskoActivity)
        presenter?.masuk(token)
        getDetailPosko()?.let { presenter?.keluar(it.id) }
        getDetailPosko()?.let { presenter?.penerimaan(it.id) }
        getDetailPosko()?.let { presenter?.penyaluran(it.id) }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}