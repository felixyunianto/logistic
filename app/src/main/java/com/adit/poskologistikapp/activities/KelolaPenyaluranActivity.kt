package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.contracts.PenyaluranContract
import com.adit.poskologistikapp.databinding.ActivityKelolaPenyaluranBinding
import com.adit.poskologistikapp.databinding.ActivityPenerimaanBinding
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.Penyaluran
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.presenters.KelolaPenyaluranPresenter
import com.adit.poskologistikapp.presenters.PenyaluranPresenter
import com.adit.poskologistikapp.utilities.Constants

class KelolaPenyaluranActivity : AppCompatActivity(), PenyaluranContract.CreateOrUpdateView {
    private lateinit var binding : ActivityKelolaPenyaluranBinding
    private var presenter : PenyaluranContract.CreateOrUpdatePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaPenyaluranBinding.inflate(layoutInflater)
        presenter = KelolaPenyaluranPresenter(this)
        setContentView(binding.root)
        setupSpinner()
        doSave()
        fill()
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getPenyaluran() : Penyaluran? = intent.getParcelableExtra("PENYALURAN")

    override fun attachToSpinner(produk : List<Logistik>) {
        val adapterPosko = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, produk)
        binding.spinnerProduk.adapter = adapterPosko

        if(!isNew()){
            for(item in produk.indices){
                if(produk[item].id == getPenyaluran()?.penerima_id){
                    binding.spinnerProduk.setSelection(item)
                }
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@KelolaPenyaluranActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loading.apply {
            visibility = View.VISIBLE
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.loading.apply {
            visibility = View.GONE
            isIndeterminate = false
            progress = 0
        }
    }

    override fun success() {
        val intent = Intent(this@KelolaPenyaluranActivity, LogistikKeluarActivity::class.java)
        startActivity(intent).also {
            finish()
        }
    }

    private fun setupSpinner(){
        val spinnerJenisAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(
            R.array.jenis_kebutuhan
        ))
        binding.jenis.adapter = spinnerJenisAdapter

        val spinnerSatuanAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(
            R.array.satuan
        ))
        binding.spinnerSatuan.adapter = spinnerSatuanAdapter

        val spinnerStatusAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(
            R.array.status
        ))
        binding.spinnerStatus.adapter = spinnerStatusAdapter

        if(!isNew()){
            val selectedJenis = spinnerJenisAdapter.getPosition(getPenyaluran()?.jenis_kebutuhan)
            binding.jenis.setSelection(selectedJenis)

            val selectedSatuan = spinnerSatuanAdapter.getPosition(getPenyaluran()?.satuan)
            binding.spinnerSatuan.setSelection(selectedSatuan)

            val selectedStatus = spinnerStatusAdapter.getPosition(getPenyaluran()?.status)
            binding.spinnerStatus.setSelection(selectedStatus)
        }
    }

    private fun getData(){
        val token = Constants.getToken(this@KelolaPenyaluranActivity)
        presenter?.getProduk(token)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    private fun fill(){
        binding.etKeterangan.setText(getPenyaluran()?.keterangan)
        binding.etJumlah.setText(getPenyaluran()?.jumlah)
        binding.etTanggal.setText(getPenyaluran()?.tanggal)
        binding.etTerima.setText(getPenyaluran()?.penerima)
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = Constants.getToken(this)
            val jenis_kebutuhan = binding.jenis.selectedItem.toString()
            val keterangan = binding.etKeterangan.text.toString()
            val jumlah = binding.etJumlah.text.toString()
            val satuan = binding.spinnerSatuan.selectedItem.toString()
            val status = binding.spinnerStatus.selectedItem.toString()
            val tanggal = binding.etTanggal.text.toString()

            val objectProduk = binding.spinnerProduk.selectedItem as Logistik
            val id_produk = objectProduk.id

            val penerima = binding.etTerima.text.toString()

            if(isNew()){
                presenter?.create(token, jenis_kebutuhan, keterangan, jumlah, status, satuan, tanggal, id_produk, penerima)
            }
        }
    }

}