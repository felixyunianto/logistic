package com.adit.poskologistikapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.adapters.ViewAdapterPemberitahuan
import com.adit.poskologistikapp.databinding.FragmentPemberitahuanBinding

class PemberitahuanFragment : Fragment() {
    private lateinit var _binding : FragmentPemberitahuanBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPemberitahuanBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        setupViewPage()
        return binding.root
    }

    private fun setupViewPage(){
        binding.viewPager.adapter = ViewAdapterPemberitahuan(getChildFragmentManager())
        binding.TabLayout.setupWithViewPager(binding.viewPager)
    }


}