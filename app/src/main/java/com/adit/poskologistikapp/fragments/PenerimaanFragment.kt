package com.adit.poskologistikapp.fragments

import android.os.Bundle
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
import com.adit.poskologistikapp.presenters.PenerimaanFragmentPresenter
import com.adit.poskologistikapp.presenters.PenerimaanKeluarFragmentPresenter
import com.adit.poskologistikapp.utilities.Constants

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
}