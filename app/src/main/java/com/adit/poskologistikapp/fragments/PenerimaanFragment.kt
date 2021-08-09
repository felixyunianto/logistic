package com.adit.poskologistikapp.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.adapters.PenerimaanAdapter
import com.adit.poskologistikapp.contracts.PenerimaanContract
import com.adit.poskologistikapp.contracts.PenerimaanKeluarFragmentContract
import com.adit.poskologistikapp.databinding.FragmentKeluarPenerimaanBinding
import com.adit.poskologistikapp.databinding.FragmentPenerimaanBinding
import com.adit.poskologistikapp.models.Penerimaan
import com.adit.poskologistikapp.models.User
import com.adit.poskologistikapp.presenters.PenerimaanFragmentPresenter
import com.adit.poskologistikapp.presenters.PenerimaanKeluarFragmentPresenter
import com.adit.poskologistikapp.utilities.Constants
import com.google.gson.Gson
import java.util.*

class PenerimaanFragment : Fragment(), PenerimaanContract.View {
    private lateinit var _binding : FragmentPenerimaanBinding
    private val binding get() = _binding
    private var presenter : PenerimaanContract.presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPenerimaanBinding.inflate(inflater, container, false)
        presenter = PenerimaanFragmentPresenter(this)

        binding.etTanggalAwal.inputType = InputType.TYPE_NULL
        binding.etTanggalAkhir.inputType = InputType.TYPE_NULL


        openDatePickerAwal()
        openDatePickerAkhir()
        printPdf()

        return binding.root
    }

    override fun attachToRecycler(penerimaan: List<Penerimaan>) {
        binding.rvLogistikKeluar.apply {
            adapter = PenerimaanAdapter(penerimaan)
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loading.isIndeterminate= true
    }

    override fun hideLoading() {
        binding.loading.apply {
            isIndeterminate= false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun emptyData() {
        binding.tvEmptyData.visibility = View.VISIBLE
    }

    private fun getData(){
        val token = Constants.getToken(requireActivity())
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
            val datePicker = DatePickerDialog(requireActivity(), { _, year, month, dayOfMonth ->
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
            val datePicker = DatePickerDialog(requireActivity(), { _, year, month, dayOfMonth ->
                binding.etTanggalAkhir.setText("$year-${month + 1}-$dayOfMonth")
            }, year, month, day)

            datePicker.show()
        }
    }

    private fun printPdf(){
        binding.btnPrint.setOnClickListener{
            var list = Constants.getList(requireActivity())
            var user = Gson().fromJson(list, User::class.java)

            val tanggal_awal = binding.etTanggalAwal.text.toString()
            val tanggal_akhir = binding.etTanggalAkhir.text.toString()
            val intent = Intent(Intent.ACTION_VIEW)
            val link = Constants.API_ENDPOINT+"print-penerimaan/${user.id_posko}?tanggal_awal=${tanggal_awal}&tanggal_akhir=${tanggal_akhir}"
            startActivity(intent.setData(Uri.parse(link)))
        }
    }
}