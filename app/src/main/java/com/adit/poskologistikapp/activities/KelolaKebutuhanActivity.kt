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
import com.adit.poskologistikapp.contracts.KebutuhanActivityContract
import com.adit.poskologistikapp.databinding.ActivityKelolaKebutuhanBinding
import com.adit.poskologistikapp.models.Kebutuhan
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.LogistikMasuk
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.presenters.KelolaKebutuhanPresenter
import com.adit.poskologistikapp.utilities.Constants
import java.util.*

class KelolaKebutuhanActivity : AppCompatActivity(), KebutuhanActivityContract.CreateOrUpdateView {
    private lateinit var binding : ActivityKelolaKebutuhanBinding
    private var presenter : KebutuhanActivityContract.CreateOrUpdateInteraction? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaKebutuhanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpinner()
        presenter = KelolaKebutuhanPresenter(this)
        binding.etTanggal.inputType = InputType.TYPE_NULL
        openDatePicker()
        doSave()
        fill()
        supportActionBar?.hide()
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getKebuthan() : Kebutuhan? = intent.getParcelableExtra("KEBUTUHAN")

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

        val spinnerStatusAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(
            R.array.terima
        ))
        binding.spinnerStatus.adapter = spinnerStatusAdapter

        if(!isNew()){
            val selectedJenis = spinnerJenisAdapter.getPosition(getKebuthan()?.jenis_kebutuhan)
            binding.jenis.setSelection(selectedJenis)

            val selectedSatuan = spinnerSatuanAdapter.getPosition(getKebuthan()?.satuan)
            binding.spinnerSatuan.setSelection(selectedSatuan)

            val selectedStatus = spinnerStatusAdapter.getPosition(getKebuthan()?.status)
            binding.spinnerStatus.setSelection(selectedStatus)
        }
    }

    override fun showToast(message: String?) {
        Toast.makeText(this@KelolaKebutuhanActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loading.apply {
            visibility = View.VISIBLE
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.loading.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun success() {
        val intent = Intent(this@KelolaKebutuhanActivity, KebutuhanActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    override fun attachToSpinner(produk: List<Logistik>) {
        val spinnerProdukAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, produk)
        binding.spinnerProduk.adapter = spinnerProdukAdapter
    }

    private fun getData(){
        val token = Constants.getToken(this@KelolaKebutuhanActivity)
        presenter?.getPosko(token)
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
            binding.etKeterangan.setText(getKebuthan()?.keterangan)
            binding.etJumlah.setText(getKebuthan()?.jumlah.toString())
            binding.etTanggal.setText(getKebuthan()?.tanggal)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = Constants.getToken(this)
            val jenis_kebutuhan = binding.jenis.selectedItem.toString()
            val keterangan = binding.etKeterangan.text.toString()
            val jumlah = binding.etJumlah.text.toString()
            val satuan = binding.spinnerSatuan.selectedItem.toString()
            val status = "Belum Terpenuhi"
            val tanggal = binding.etTanggal.text.toString()

            val objectProduk = binding.spinnerProduk.selectedItem as Logistik

            val id_produk = objectProduk.id

            if(isNew()){
                presenter?.create(token, id_produk, jenis_kebutuhan, keterangan, jumlah, status, tanggal, satuan)
            }else{
                presenter?.update(token, getKebuthan()?.id.toString(), id_produk, jenis_kebutuhan, keterangan, jumlah, status, tanggal, satuan)
            }
        }
    }
}