package com.adit.poskologistikapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.api.load
import com.adit.poskologistikapp.databinding.ActivityDetailBencanaBinding
import com.adit.poskologistikapp.models.Bencana

class DetailBencanaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBencanaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityDetailBencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        parseToView()
    }

    private fun getBencanaDetail() : Bencana? = intent.getParcelableExtra("BENCANA_DETAIL")

    private fun parseToView() {
        binding.tvNamaBencana.text = getBencanaDetail()?.nama
        binding.tvTanggalBencana.text = getBencanaDetail()?.tanggal
        binding.tvDetailBencana.text = getBencanaDetail()?.detail

        binding.ivImage.load(getBencanaDetail()?.foto)
    }
}