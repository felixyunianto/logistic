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
import com.adit.poskologistikapp.contracts.BencanaActivityContract
import com.adit.poskologistikapp.databinding.FragmentBencanaPemberitahuanBinding
import com.adit.poskologistikapp.models.Bencana
import com.adit.poskologistikapp.presenters.BencanaPemberitahuanPresenter

class BencanaPemberitahuanFragment : Fragment(), BencanaActivityContract.BencanaActivityView {
    private lateinit var _binding : FragmentBencanaPemberitahuanBinding
    private var presenter : BencanaActivityContract.BencanaActivityPresenter? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBencanaPemberitahuanBinding.inflate(inflater, container, false)
        presenter = BencanaPemberitahuanPresenter(this)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun attachToRecycle(bencana: List<Bencana>) {
        binding.rvBencana.apply {
            adapter = BencanaPemberitahuanAdapter(bencana)
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