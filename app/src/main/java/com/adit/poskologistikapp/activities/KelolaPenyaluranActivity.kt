package com.adit.poskologistikapp.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class KelolaPenyaluranActivity : AppCompatActivity(), PenyaluranContract.CreateOrUpdateView {
    private lateinit var binding : ActivityKelolaPenyaluranBinding
    private var presenter : PenyaluranContract.CreateOrUpdatePresenter? = null
    private lateinit var sheenValidator : SheenValidator
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime = LocalDateTime.now()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaPenyaluranBinding.inflate(layoutInflater)
        presenter = KelolaPenyaluranPresenter(this)
        sheenValidator = SheenValidator(this)
        setContentView(binding.root)
        setupSpinner()
        doSave()
        fill()
        supportActionBar?.hide()

        binding.etTanggal.setText(currentDateTime.format(DateTimeFormatter.ISO_DATE))
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getPenyaluran() : Penyaluran? = intent.getParcelableExtra("PENYALURAN")

    override fun attachToSpinner(produk : List<Logistik>) {
        val adapterPosko = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, produk)
        binding.spinnerProduk.adapter = adapterPosko

        if(!isNew()){
            for(item in produk.indices){
                if(produk[item].id == getPenyaluran()?.id_produk){
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
        val intent = Intent(this@KelolaPenyaluranActivity, PenyaluranActivity::class.java)
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
        if(!isNew()){
            binding.etKeterangan.setText(getPenyaluran()?.keterangan)
            binding.etJumlah.setText(getPenyaluran()?.jumlah)
            binding.etTanggal.setText(getPenyaluran()?.tanggal)
            binding.etTerima.setText(getPenyaluran()?.penerima)
        }
    }

    private fun doSave(){
        sheenValidator.setOnValidatorListener {
            showLoading()
            val token = Constants.getToken(this)
            val objectProduk = binding.spinnerProduk.selectedItem as Logistik

            val jenis_kebutuhan = binding.jenis.selectedItem.toString()
            val keterangan = binding.etKeterangan.text.toString()
            val jumlah = binding.etJumlah.text.toString()
            val satuan = objectProduk.satuan
            val status = "Terima"
            val tanggal = binding.etTanggal.text.toString()


            val id_produk = objectProduk.id

            val penerima = binding.etTerima.text.toString()

            if(isNew()){
                presenter?.create(token, jenis_kebutuhan, keterangan, jumlah, status, satuan, tanggal, id_produk, penerima)
            }else{
                presenter?.edit(token, getPenyaluran()?.id.toString() , jenis_kebutuhan, keterangan, jumlah, status, satuan, tanggal, id_produk, penerima)
            }
        }

        sheenValidator.registerAsRequired(binding.etKeterangan)
        sheenValidator.registerAsRequired(binding.etJumlah)
        sheenValidator.registerAsRequired(binding.etTerima)


        binding.btnSubmit.setOnClickListener {
            sheenValidator.validate()
        }
    }

}