package com.adit.poskologistikapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.activities.*
import com.adit.poskologistikapp.databinding.FragmentHomeAdminBinding
import com.adit.poskologistikapp.databinding.FragmentHomePetugasBinding
import com.adit.poskologistikapp.models.User
import com.adit.poskologistikapp.utilities.Constants
import com.google.gson.Gson

class HomePetugasFragment : Fragment() {
    private lateinit var _binding : FragmentHomePetugasBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomePetugasBinding.inflate(inflater, container, false)
        buttonClick()
        return binding.root
    }
    private fun buttonClick(){
        binding.penerimaan.setOnClickListener {
            startActivity(Intent(requireActivity(), PenerimaanActivity::class.java))
        }

        binding.penyaluran.setOnClickListener {
            startActivity(Intent(requireActivity(), PenyaluranActivity::class.java))
        }

        binding.posko.setOnClickListener {
            startActivity(Intent(requireActivity(), PoskoActivity::class.java))
        }

        binding.donatur.setOnClickListener {
            startActivity(Intent(requireActivity(), DonaturActivity::class.java))
        }

        binding.kebutuhan.setOnClickListener {
            val list = Constants.getList(requireActivity())
            val user = Gson().fromJson(list, User::class.java)

            startActivity(Intent(requireActivity(), KebutuhanActivity::class.java).apply {
                putExtra("ID_POSKO", user.id_posko)
            })
        }

        binding.produk.setOnClickListener {
            val list = Constants.getList(requireActivity())
            val user = Gson().fromJson(list, User::class.java)
            startActivity(Intent(requireActivity(), LogistikProdukActivity::class.java).apply {
                putExtra("IS_FROM_BERANDA", true)
                putExtra("ID_POSKO", user.id_posko)
            })
        }
    }
}