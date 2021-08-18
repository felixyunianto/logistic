package com.adit.poskologistikapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.activities.EditProfileActivity
import com.adit.poskologistikapp.activities.LoginActivity
import com.adit.poskologistikapp.activities.MainActivity
import com.adit.poskologistikapp.databinding.FragmentProfileBinding
import com.adit.poskologistikapp.models.User
import com.adit.poskologistikapp.utilities.Constants
import com.google.gson.Gson

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
        parseToView()
        moveEditProfile()
        return binding.root
    }

    private fun showButton(){
        val token = Constants.getToken(requireActivity())
        if(token != "UNDEFINED"){
            binding.login.visibility = View.GONE
            binding.logout.visibility = View.VISIBLE
            binding.editProfile.visibility = View.VISIBLE
            binding.cvProfile.visibility = View.VISIBLE
        }else{
            binding.login.visibility = View.VISIBLE
            binding.logout.visibility = View.GONE
            binding.editProfile.visibility = View.GONE
            binding.cvProfile.visibility = View.GONE
        }
    }

    private fun parseToView(){
        val token = Constants.getToken(requireActivity())
        if(token != "UNDEFINED"){
            val listUser : User;
            val list = Constants.getList(requireActivity())
            listUser = Gson().fromJson(list, User::class.java)

            binding.tvUsername.text = listUser.username
            binding.tvLevel.text = listUser.level
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

    private fun moveEditProfile() {
        binding.editProfile.setOnClickListener {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkAuthentication(){
        val token = Constants.getToken(requireActivity())
        if(token == "UNDEFINED"){
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }
}