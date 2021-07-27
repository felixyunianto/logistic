package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.contracts.LogistikProdukActivityContract
import com.adit.poskologistikapp.databinding.ActivityKelolaLogistikBinding
import com.adit.poskologistikapp.databinding.ActivityLogistikProdukBinding
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.presenters.KelolaLogistikProdukActivityPresenter
import com.adit.poskologistikapp.utilities.Constants

class KelolaLogistikActivity : AppCompatActivity(), LogistikProdukActivityContract.CreateOrUpdateView {
    private lateinit var binding : ActivityKelolaLogistikBinding
    private var presenter : LogistikProdukActivityContract.CreateOrUpdatePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaLogistikBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupSpinner()
        fill()
        doSave()
        presenter = KelolaLogistikProdukActivityPresenter(this)
    }

    override fun showToast(message: String) {
        Toast.makeText(this@KelolaLogistikActivity, message, Toast.LENGTH_LONG).show()
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
        val intent = Intent(this@KelolaLogistikActivity, LogistikProdukActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getLogistik() : Logistik? = intent.getParcelableExtra("LOGISTIK")

    private fun setupSpinner(){
        val spinnerSatuanAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(
            R.array.satuan
        ))
        binding.spinnerSatuan.adapter = spinnerSatuanAdapter

        if(!isNew()){
            var selectedSatuan = spinnerSatuanAdapter.getPosition(getLogistik()?.satuan)
            binding.spinnerSatuan.setSelection(selectedSatuan)
        }
    }

    private fun fill(){
        if(!isNew()){
            binding.etNama.setText(getLogistik()?.nama_produk)
            binding.etJumlah.setText(getLogistik()?.jumlah)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = Constants.getToken(this)
            val nama_produk = binding.etNama.text.toString()
            val jumlah = binding.etJumlah.text.toString()
            val satuan = binding.spinnerSatuan.selectedItem.toString()

            if(isNew()){
                presenter?.create(token, nama_produk, jumlah, satuan)
            }else{
                presenter?.update(token, getLogistik()?.id.toString(), nama_produk, jumlah, satuan)
            }
        }
    }


}