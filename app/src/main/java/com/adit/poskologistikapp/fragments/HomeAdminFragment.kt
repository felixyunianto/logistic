package com.adit.poskologistikapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.adit.poskologistikapp.activities.*
import com.adit.poskologistikapp.databinding.FragmentHomeAdminBinding

class HomeAdminFragment : Fragment() {
    private lateinit var _binding : FragmentHomeAdminBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeAdminBinding.inflate(inflater, container, false)
        buttonClick()
        return binding.root
    }

    private fun buttonClick() {
        binding.petugas.setOnClickListener {
            startActivity(Intent(requireActivity(), PetugasActivity::class.java))
        }

        binding.posko.setOnClickListener {
            startActivity(Intent(requireActivity(), PoskoActivity::class.java))
        }

        binding.bencana.setOnClickListener {
            startActivity(Intent(requireActivity(), BencanaActivity::class.java))
        }

        binding.donatur.setOnClickListener {
            startActivity(Intent(requireActivity(), DonaturActivity::class.java))
        }

        binding.distribusi.setOnClickListener {
            startActivity(Intent(requireActivity(), DistribusiActivity::class.java))
        }

        binding.logistik.setOnClickListener {
            startActivity(Intent(requireActivity(), LogistikProdukActivity::class.java))
        }
    }
}