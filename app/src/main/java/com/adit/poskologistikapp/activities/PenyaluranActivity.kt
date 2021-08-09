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
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.adapters.PenyaluranAdapter
import com.adit.poskologistikapp.contracts.PenyaluranContract
import com.adit.poskologistikapp.databinding.ActivityPenyaluranBinding
import com.adit.poskologistikapp.models.Penyaluran
import com.adit.poskologistikapp.models.User
import com.adit.poskologistikapp.presenters.PenyaluranPresenter
import com.adit.poskologistikapp.utilities.Constants
import com.google.gson.Gson
import java.util.*

class PenyaluranActivity : AppCompatActivity(), PenyaluranContract.View {
    private lateinit var binding : ActivityPenyaluranBinding
    private var presenter : PenyaluranContract.presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenyaluranBinding.inflate(layoutInflater)
        presenter = PenyaluranPresenter(this)
        setContentView(binding.root)
        supportActionBar?.title = "Penyaluran Logistik"

        binding.fab.setOnClickListener{
            val intent = Intent(this@PenyaluranActivity, KelolaPenyaluranActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }

            startActivity(intent)
        }

        binding.etTanggalAwal.inputType = InputType.TYPE_NULL
        binding.etTanggalAkhir.inputType = InputType.TYPE_NULL


        openDatePickerAwal()
        openDatePickerAkhir()
        printPdf()
    }

    override fun attachToRecycler(penyaluran: List<Penyaluran>) {
        binding.rvPetugas.apply {
            adapter = PenyaluranAdapter(penyaluran)
            layoutManager = LinearLayoutManager(this@PenyaluranActivity)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@PenyaluranActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loading.isIndeterminate = true
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
        presenter?.getLogistikProduk(token)
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
            var list = Constants.getList(this)
            var user = Gson().fromJson(list, User::class.java)
            val tanggal_awal = binding.etTanggalAwal.text.toString()
            val tanggal_akhir = binding.etTanggalAkhir.text.toString()
            val intent = Intent(Intent.ACTION_VIEW)
            val link = Constants.API_ENDPOINT+"print-penyaluran/${user.id_posko}?tanggal_awal=${tanggal_awal}&tanggal_akhir=${tanggal_akhir}"
            startActivity(intent.setData(Uri.parse(link)))
        }
    }
}