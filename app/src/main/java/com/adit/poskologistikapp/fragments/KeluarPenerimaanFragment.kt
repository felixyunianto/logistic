package com.adit.poskologistikapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.adapters.PenerimaanKeluarAdapter
import com.adit.poskologistikapp.adapters.onClickPenerimaanKeluarAdapter
import com.adit.poskologistikapp.contracts.PenerimaanKeluarFragmentContract
import com.adit.poskologistikapp.databinding.FragmentKeluarPenerimaanBinding
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.presenters.PenerimaanKeluarFragmentPresenter
import com.adit.poskologistikapp.utilities.Constants

class KeluarPenerimaanFragment : Fragment(), PenerimaanKeluarFragmentContract.PenerimaanFragmentView {
    private lateinit var _binding : FragmentKeluarPenerimaanBinding
    private val binding get() = _binding
    private var presenter : PenerimaanKeluarFragmentContract.PenerimaanFragmentPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentKeluarPenerimaanBinding.inflate(inflater, container, false)
        presenter = PenerimaanKeluarFragmentPresenter(this)
        return binding.root
    }


    override fun attachToRecycler(keluar: List<LogistikKeluar>) {
        binding.rvLogistikKeluar.apply {
            adapter = PenerimaanKeluarAdapter(keluar, object : onClickPenerimaanKeluarAdapter{
                override fun konfirmasi(logistik: LogistikKeluar) {
                    showLoading()
                    val token = Constants.getToken(requireActivity())
                    presenter?.konfirmasi(token, logistik.id)
                }

            })

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
        presenter?.infoPenerimaanKeluar(token)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

}