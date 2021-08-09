package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.adapters.PoskoMasyarakatAdapter
import com.adit.poskologistikapp.adapters.onClickAdapter
import com.adit.poskologistikapp.contracts.PoskoActivityContract
import com.adit.poskologistikapp.databinding.ActivityPoskoMasyarakatBinding
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.presenters.PoskoMasyarakatPresenter

class PoskoMasyarakatActivity : AppCompatActivity() , PoskoActivityContract.PoskoMasyarakatView{
    private lateinit var binding : ActivityPoskoMasyarakatBinding
    private var presenter : PoskoActivityContract.PoskoMasyarakatPresenter? = null
    private lateinit var adapterPoskoMasyarakat : PoskoMasyarakatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoskoMasyarakatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = PoskoMasyarakatPresenter(this)
    }

    override fun attachToRecycler(posko: List<Posko>) {
        adapterPoskoMasyarakat = PoskoMasyarakatAdapter(posko, object: onClickAdapter{
            override fun donatur(posko: Posko) {
                val intent = Intent(this@PoskoMasyarakatActivity, DonaturByPoskoActivity::class.java).apply {
                    putExtra("ID_POSKO", posko.id)
                }

                startActivity(intent)
            }

        })
        binding.rvDonaturByPosko.apply {
            adapter = adapterPoskoMasyarakat
            layoutManager = LinearLayoutManager(this@PoskoMasyarakatActivity)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@PoskoMasyarakatActivity, message, Toast.LENGTH_LONG).show()
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
        presenter?.infoPosko()
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