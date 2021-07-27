package com.adit.poskologistikapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.adapters.BencanaPemberitahuanAdapter
import com.adit.poskologistikapp.adapters.KebutuhanPemberitahuanAdapter
import com.adit.poskologistikapp.contracts.KebutuhanPemberitahuanContract
import com.adit.poskologistikapp.databinding.FragmentKebutuhanPemberitahuanBinding
import com.adit.poskologistikapp.models.Kebutuhan
import com.adit.poskologistikapp.presenters.KebutuhanPemberitahuanPresenter

class KebutuhanPemberitahuanFragment : Fragment(), KebutuhanPemberitahuanContract.KebutuhanActivityView {
    private lateinit var _binding : FragmentKebutuhanPemberitahuanBinding
    private val binding get() = _binding
    private var presenter : KebutuhanPemberitahuanContract.KebutuhanActivityPresenter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKebutuhanPemberitahuanBinding.inflate(inflater, container, false)
        presenter = KebutuhanPemberitahuanPresenter(this)
        return binding.root
    }

    override fun attachToRecycle(kebutuhan: List<Kebutuhan>) {
        binding.rvBencana.apply {
            adapter = KebutuhanPemberitahuanAdapter(kebutuhan)
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
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
        presenter?.infoBencana()
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