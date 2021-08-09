package com.adit.poskologistikapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.adapters.DonaturByPoskoAdapter
import com.adit.poskologistikapp.contracts.DonaturActivityContract
import com.adit.poskologistikapp.databinding.ActivityDonaturByPoskoBinding
import com.adit.poskologistikapp.models.Donatur
import com.adit.poskologistikapp.presenters.DonaturByPoskoPresenter

class DonaturByPoskoActivity : AppCompatActivity(), DonaturActivityContract.DonaturByPoskoView {
    private lateinit var binding : ActivityDonaturByPoskoBinding
    private var presenter : DonaturActivityContract.DonaturByPoskoPresenter? = null
    private lateinit var adapterDonaturByPosko : DonaturByPoskoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonaturByPoskoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = DonaturByPoskoPresenter(this)
        supportActionBar?.hide()
    }

    override fun showToast(message: String?) {
        Toast.makeText(this@DonaturByPoskoActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun attachToRecycler(donatur: List<Donatur>) {
        adapterDonaturByPosko = DonaturByPoskoAdapter(donatur)
        binding.rvDonaturByPosko.apply {
            adapter = adapterDonaturByPosko
            layoutManager = LinearLayoutManager(this@DonaturByPoskoActivity)
        }
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
        val id_posko = intent.getStringExtra("ID_POSKO")
        presenter?.infoDonaturByPosko(id_posko.toString())
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