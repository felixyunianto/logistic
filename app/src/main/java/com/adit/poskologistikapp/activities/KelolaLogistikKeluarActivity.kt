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
import com.adit.poskologistikapp.contracts.LogistikKeluarActivityContract
import com.adit.poskologistikapp.databinding.ActivityKelolaLogistikKeluarBinding
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.presenters.KelolaLogistikKeluarActivityPresenter
import com.adit.poskologistikapp.presenters.LogistikKeluarActivityPresenter
import com.adit.poskologistikapp.utilities.Constants
import java.util.*

class KelolaLogistikKeluarActivity : AppCompatActivity(), LogistikKeluarActivityContract.CreateOrUpdateView {
    private lateinit var binding : ActivityKelolaLogistikKeluarBinding
    private var presenter : LogistikKeluarActivityContract.CreateOrUpdatePresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaLogistikKeluarBinding.inflate(layoutInflater)
        presenter = KelolaLogistikKeluarActivityPresenter(this)
        setContentView(binding.root)
        setupSpinner()
        doSave()
        fill()
        binding.etTanggal.inputType = InputType.TYPE_NULL
        openDatePicker()
        supportActionBar?.hide()
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)
    private fun getKeluar() : LogistikKeluar? = intent.getParcelableExtra("KELUAR")

    override fun attachToSpinner(posko: List<Posko>) {
        val adapterPosko = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, posko)
        binding.spinnerPosko.adapter = adapterPosko

        if(!isNew()){
            for(item in posko.indices){
                if(posko[item].id == getKeluar()?.penerima_id){
                    binding.spinnerPosko.setSelection(item)
                }
            }
        }
    }

    override fun attachToSpinnerProduk(produk: List<Logistik>) {
        val adapterProduk = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, produk)
        binding.spinnerProduk.adapter = adapterProduk

        if(!isNew()){
            for(item in produk.indices){
                if(produk[item].id == getKeluar()?.id_produk){
                    binding.spinnerProduk.setSelection(item)
                }
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@KelolaLogistikKeluarActivity, message, Toast.LENGTH_LONG).show()
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
        val intent = Intent(this@KelolaLogistikKeluarActivity, LogistikKeluarActivity::class.java)
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

            datePicker.datePicker.maxDate = c.timeInMillis

            c.add(Calendar.MONTH, -6)

            datePicker.datePicker.minDate = c.timeInMillis

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
            R.array.status
        ))
        binding.spinnerStatus.adapter = spinnerStatusAdapter

        if(!isNew()){
            val selectedJenis = spinnerJenisAdapter.getPosition(getKeluar()?.jenis_kebutuhan)
            binding.jenis.setSelection(selectedJenis)

            val selectedSatuan = spinnerSatuanAdapter.getPosition(getKeluar()?.satuan)
            binding.spinnerSatuan.setSelection(selectedSatuan)

            val selectedStatus = spinnerStatusAdapter.getPosition(getKeluar()?.status)
            binding.spinnerStatus.setSelection(selectedStatus)
        }
    }

    private fun getData(){
        val token = Constants.getToken(this@KelolaLogistikKeluarActivity)
        presenter?.getPosko()
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
            binding.etKeterangan.setText(getKeluar()?.keterangan)
            binding.etJumlah.setText(getKeluar()?.jumlah)
            binding.etTanggal.setText(getKeluar()?.tanggal)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = Constants.getToken(this)
            val jenis_kebutuhan = binding.jenis.selectedItem.toString()
            val keterangan = binding.etKeterangan.text.toString()
            val jumlah = binding.etJumlah.text.toString()
            val satuan = binding.spinnerSatuan.selectedItem.toString()
            val status = "Proses"
            val tanggal = binding.etTanggal.text.toString()

            val objectProduk = binding.spinnerProduk.selectedItem as Logistik
            val objectPosko = binding.spinnerPosko.selectedItem as Posko

            val id_produk = objectProduk.id
            val penerima_id = objectPosko.id

            if(isNew()){
                presenter?.create(token, jenis_kebutuhan, keterangan, jumlah, status, satuan, tanggal, id_produk, penerima_id )
            }else{
                presenter?.edit(token, getKeluar()?.id.toString(), jenis_kebutuhan, keterangan, jumlah, status, satuan, tanggal, id_produk, penerima_id)
            }
        }
    }
}