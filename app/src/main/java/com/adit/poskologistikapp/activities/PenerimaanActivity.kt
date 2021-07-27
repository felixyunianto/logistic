package com.adit.poskologistikapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adit.poskologistikapp.adapters.ViewPagerAdapter
import com.adit.poskologistikapp.databinding.ActivityPenerimaanBinding

class PenerimaanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPenerimaanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenerimaanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupViewPage()
    }

    private fun setupViewPage(){
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.TabLayout.setupWithViewPager(binding.viewPager)
    }

}