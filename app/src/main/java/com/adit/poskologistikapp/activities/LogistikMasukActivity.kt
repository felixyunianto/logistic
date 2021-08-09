package com.adit.poskologistikapp.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.adapters.LogistikMasukActivityAdapter
import com.adit.poskologistikapp.adapters.onClickMasukAdapter
import com.adit.poskologistikapp.contracts.LogistikMasukActivityContract
import com.adit.poskologistikapp.databinding.ActivityLogistikMasukBinding
import com.adit.poskologistikapp.models.LogistikMasuk
import com.adit.poskologistikapp.presenters.LogistikMasukActivityPresenter
import com.adit.poskologistikapp.utilities.APIClient
import com.adit.poskologistikapp.utilities.Constants
import java.time.LocalDateTime
import java.util.*

class LogistikMasukActivity : AppCompatActivity(), LogistikMasukActivityContract.LogistikMasukActivityView {
    private lateinit var binding : ActivityLogistikMasukBinding
    private lateinit var adapterMasuk : LogistikMasukActivityAdapter
    private var presenter : LogistikMasukActivityContract.LogistikMasukActivityPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Logistik Masuk"
        binding = ActivityLogistikMasukBinding.inflate(layoutInflater)
        presenter = LogistikMasukActivityPresenter(this)
        setContentView(binding.root)
        printPdf()
        binding.fab.setOnClickListener {
            val intent = Intent(this@LogistikMasukActivity, KelolaLogistikMasukActivity::class.java).apply {
                putExtra("IS_NEW", true)
                putExtra("KONDISI", "baru")
            }

            startActivity(intent).also {
                finish();
            }
        }
        binding.etTanggalAwal.inputType = InputType.TYPE_NULL
        binding.etTanggalAkhir.inputType = InputType.TYPE_NULL


        openDatePickerAwal()
        openDatePickerAkhir()
    }

    override fun attachToRecycler(logistik_masuk: List<LogistikMasuk>) {
        adapterMasuk = LogistikMasukActivityAdapter(logistik_masuk, object : onClickMasukAdapter{
            override fun edit(logistik_masuk: LogistikMasuk) {
                val intent = Intent(this@LogistikMasukActivity, KelolaLogistikMasukActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("MASUK", logistik_masuk)
                    putExtra("KONDISI", "lama")
                }

                startActivity(intent).also {
                    finish();
                }
            }

            override fun delete(logistik_masuk: LogistikMasuk) {
                delete(logistik_masuk.id)
            }

        })

        binding.rvLogistikMasuk.apply {
            adapter = adapterMasuk
            layoutManager = LinearLayoutManager(this@LogistikMasukActivity)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@LogistikMasukActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loading.isIndeterminate = true
    }

    private fun delete(id : String){
        val token = Constants.getToken(this)
        presenter?.delete(token, id)
    }

    override fun hideLoading() {
        binding.loading.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun emptyData() {
        binding.tvEmptyData.visibility = View.VISIBLE
    }

    private fun getData(){
        val token = Constants.getToken(this)
        presenter?.getLogistikMasuk(token)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    private fun openDatePickerAwal(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.etTanggalAwal.setOnClickListener {
            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                binding.etTanggalAwal.setText("$year-${month+1}-$dayOfMonth")
            }, year, month, day)

            datePicker.show()
        }
    }

    private fun openDatePickerAkhir() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.etTanggalAkhir.setOnClickListener {
            val datePicker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                binding.etTanggalAkhir.setText("$year-${month + 1}-$dayOfMonth")
            }, year, month, day)

            datePicker.show()
        }
    }

    private fun printPdf(){
        binding.btnPrint.setOnClickListener{
            val tanggal_awal = binding.etTanggalAwal.text.toString()
            val tanggal_akhir = binding.etTanggalAkhir.text.toString()
            val intent = Intent(Intent.ACTION_VIEW)
            val link = Constants.API_ENDPOINT+"print-logistik-masuk?tanggal_awal=${tanggal_awal}&tanggal_akhir=${tanggal_akhir}"
            startActivity(intent.setData(Uri.parse(link)))
        }
    }
}