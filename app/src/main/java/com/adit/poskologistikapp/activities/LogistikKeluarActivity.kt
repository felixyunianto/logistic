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
import com.adit.poskologistikapp.adapters.LogistikKeluarActivityAdapter
import com.adit.poskologistikapp.adapters.onClickKeluarAdapter
import com.adit.poskologistikapp.contracts.LogistikKeluarActivityContract
import com.adit.poskologistikapp.databinding.ActivityLogistikKeluarBinding
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.models.User
import com.adit.poskologistikapp.presenters.LogistikKeluarActivityPresenter
import com.adit.poskologistikapp.utilities.Constants
import com.google.gson.Gson
import java.util.*

class LogistikKeluarActivity : AppCompatActivity(), LogistikKeluarActivityContract.LogistikKeluarActivityView {
    private lateinit var binding : ActivityLogistikKeluarBinding
    private lateinit var adapterKeluar : LogistikKeluarActivityAdapter
    private var presenter : LogistikKeluarActivityContract.LogistikKeluarActivityPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogistikKeluarBinding.inflate(layoutInflater)
        presenter = LogistikKeluarActivityPresenter(this)
        supportActionBar?.title = "Logistik Keluar"
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val intent = Intent(this@LogistikKeluarActivity, KelolaLogistikKeluarActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }

            startActivity(intent).also {
                finish();
            }
        }

        binding.etTanggalAwal.inputType = InputType.TYPE_NULL
        binding.etTanggalAkhir.inputType = InputType.TYPE_NULL


        openDatePickerAwal()
        openDatePickerAkhir()
        printPdf()
    }

    override fun attachToRecycler(logistik_keluar: List<LogistikKeluar>) {
        adapterKeluar = LogistikKeluarActivityAdapter(logistik_keluar, object : onClickKeluarAdapter{
            override fun edit(logistik_keluar: LogistikKeluar) {
                val intent = Intent(this@LogistikKeluarActivity, KelolaLogistikKeluarActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("KELUAR", logistik_keluar)
                }

                startActivity(intent)
            }

            override fun delete(logistik_keluar: LogistikKeluar) {
                delete(logistik_keluar.id)
            }

        })

        binding.rvLogistikKeluar.apply {
            adapter = adapterKeluar
            layoutManager = LinearLayoutManager(this@LogistikKeluarActivity)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@LogistikKeluarActivity, message, Toast.LENGTH_LONG).show()
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
        val token = Constants.getToken(this@LogistikKeluarActivity)
        presenter?.getLogistikKeluar(token)
    }

    private fun delete(id : String){
        val token = Constants.getToken(this@LogistikKeluarActivity)
        presenter?.hapusLogistikKeluar(token, id)
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
            val link = Constants.API_ENDPOINT+"print-logistik-keluar/${user.id_posko}?tanggal_awal=${tanggal_awal}&tanggal_akhir=${tanggal_akhir}"
            startActivity(intent.setData(Uri.parse(link)))
        }
    }
}