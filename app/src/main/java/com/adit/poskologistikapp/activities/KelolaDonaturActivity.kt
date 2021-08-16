package com.adit.poskologistikapp.activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.contracts.DonaturActivityContract
import com.adit.poskologistikapp.databinding.ActivityKelolaDonaturBinding
import com.adit.poskologistikapp.models.Donatur
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.presenters.DonaturActivityPresenter
import com.adit.poskologistikapp.presenters.KelolaDonaturActivityPresenter
import com.adit.poskologistikapp.utilities.Constants
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import java.util.*

class KelolaDonaturActivity : AppCompatActivity(), DonaturActivityContract.CreateOrUpdateView {
    private lateinit var binding : ActivityKelolaDonaturBinding
    private var presenter : DonaturActivityContract.CreateOrUpdateInteraction? = null
    private lateinit var sheenValidator : SheenValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityKelolaDonaturBinding.inflate(layoutInflater)
        presenter = KelolaDonaturActivityPresenter(this)
        sheenValidator = SheenValidator(this)
        setupSpinner()
        binding.etTanggal.inputType = InputType.TYPE_NULL
        setContentView(binding.root)
        fill()
        openDatePicker()
        doSave()
    }

    override fun showToast(message: String?) {
        Toast.makeText(this@KelolaDonaturActivity, message, Toast.LENGTH_LONG).show()
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

    override fun attachToSpinner(posko: List<Posko>) {
        var spinnerAdapterPosko = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, posko)
        binding.idPosko.apply {
            adapter = spinnerAdapterPosko
        }

        if(!isNew()){
            for(item in posko.indices){
                if(posko[item].id == getDonatur()?.id_posko){
                    binding.idPosko.setSelection(item)
                }
            }
        }
    }

    override fun success() {
        val intent = Intent(this@KelolaDonaturActivity, DonaturActivity::class.java)
        startActivity(intent).also {
            finish()
        }
    }

    private fun openDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.etTanggal.setOnClickListener {
            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                binding.etTanggal.setText("$year-${month+1}-$dayOfMonth")
            }, year, month, day)

            datePicker.show()
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

        if(!isNew()){
            val selectedJenis = spinnerJenisAdapter.getPosition(getDonatur()?.jenis_kebutuhan)
            binding.jenis.setSelection(selectedJenis)

            val selectedSatuan = spinnerSatuanAdapter.getPosition(getDonatur()?.satuan)
            binding.spinnerSatuan.setSelection(selectedSatuan)
        }
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getDonatur() : Donatur? = intent.getParcelableExtra("DONATUR")

    private fun fill(){
        binding.etNama.setText(getDonatur()?.nama)
        binding.etKeterangan.setText(getDonatur()?.keterangan)
        binding.etAlamat.setText(getDonatur()?.alamat)
        binding.etTanggal.setText(getDonatur()?.tanggal)
        binding.etJumlah.setText(getDonatur()?.jumlah)
    }

    private fun doSave(){
        sheenValidator.setOnValidatorListener {
            showLoading()
            val token = Constants.getToken(this@KelolaDonaturActivity)
            val nama = binding.etNama.text.toString()
            val jenis_kebutuhan = binding.jenis.selectedItem.toString()
            val keterangan = binding.etKeterangan.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val objectPosko = binding.idPosko.selectedItem as Posko
            val id_posko = objectPosko.id
            val tanggal = binding.etTanggal.text.toString()
            val jumlah = binding.etJumlah.text.toString()
            val satuan = binding.spinnerSatuan.selectedItem.toString()

            if(isNew()){
                presenter?.create(token, nama, jenis_kebutuhan, keterangan, alamat, id_posko, tanggal, jumlah, satuan)
            }else{
                presenter?.update(token, getDonatur()?.id.toString(), nama, jenis_kebutuhan, keterangan, alamat, id_posko, tanggal, jumlah, satuan)
            }
        }

        sheenValidator.registerAsRequired(binding.etNama)
        sheenValidator.registerAsRequired(binding.etKeterangan)
        sheenValidator.registerAsRequired(binding.etAlamat)
        sheenValidator.registerHasMinLength(binding.etAlamat, 4)
        sheenValidator.registerAsRequired(binding.etTanggal)
        sheenValidator.registerAsRequired(binding.etJumlah)

        binding.btnSubmit.setOnClickListener {
            sheenValidator.validate()
        }
    }

    private fun getPosko(){
        presenter?.getPosko()
    }

    override fun onResume() {
        super.onResume()
        getPosko()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}