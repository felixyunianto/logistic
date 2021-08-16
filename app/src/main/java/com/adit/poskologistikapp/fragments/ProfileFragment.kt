package com.adit.poskologistikapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.activities.LoginActivity
import com.adit.poskologistikapp.activities.MainActivity
import com.adit.poskologistikapp.databinding.FragmentProfileBinding
import com.adit.poskologistikapp.utilities.Constants

class ProfileFragment : Fragment() {
    private lateinit var _binding : FragmentProfileBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        showButton()
        buttonClick()
        return binding.root
    }

    private fun showButton(){
        val token = Constants.getToken(requireActivity())
        if(token != "UNDEFINED"){
            binding.login.visibility = View.GONE
            binding.logout.visibility = View.VISIBLE
        }else{
            binding.login.visibility = View.VISIBLE
            binding.logout.visibility = View.GONE
        }
    }

    private fun buttonClick(){
        binding.logout.setOnClickListener {
            Constants.clearToken(requireActivity())
            checkAuthentication()
            Constants.clearList(requireActivity())
        }

        binding.login.setOnClickListener {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }
    }

    private fun checkAuthentication(){
        val token = Constants.getToken(requireActivity())
        if(token == "UNDEFINED"){
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }
}