package com.adit.poskologistikapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adit.poskologistikapp.databinding.ActivityDetailPetugasBinding
import com.adit.poskologistikapp.models.Petugas

class DetailPetugasActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailPetugasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPetugasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseToView()
        supportActionBar?.hide()
    }

    private fun getDetailPetugas() : Petugas? = intent.getParcelableExtra("DETAIL_PETUGAS")

    private fun parseToView(){
        binding.tvUsername.text = getDetailPetugas()!!.username
        binding.tvLevel.text = getDetailPetugas()!!.level
        binding.tvPosko.text = getDetailPetugas()!!.nama_posko
    }
}