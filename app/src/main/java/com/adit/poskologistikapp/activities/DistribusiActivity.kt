package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adit.poskologistikapp.databinding.ActivityDistribusiBinding

class DistribusiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDistribusiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDistribusiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonClick()
    }
    private fun buttonClick(){
        binding.masuk.setOnClickListener {
            startActivity(Intent(this@DistribusiActivity, LogistikMasukActivity::class.java));
        }

        binding.keluar.setOnClickListener {
            startActivity(Intent(this@DistribusiActivity, LogistikKeluarActivity::class.java));
        }
    }
}