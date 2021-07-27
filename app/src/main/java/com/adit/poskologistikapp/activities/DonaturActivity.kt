package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.KelolaBencanaActivity
import com.adit.poskologistikapp.adapters.DonaturActivityAdapter
import com.adit.poskologistikapp.adapters.onClickDonaturAdapter
import com.adit.poskologistikapp.contracts.DonaturActivityContract
import com.adit.poskologistikapp.databinding.ActivityDonaturBinding
import com.adit.poskologistikapp.models.Donatur
import com.adit.poskologistikapp.presenters.DonaturActivityPresenter
import com.adit.poskologistikapp.utilities.Constants

class DonaturActivity : AppCompatActivity(), DonaturActivityContract.DonaturActivityView {
    private lateinit var binding : ActivityDonaturBinding
    private lateinit var adapterDonatur : DonaturActivityAdapter
    private var presenter : DonaturActivityContract.DonaturActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonaturBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = DonaturActivityPresenter(this)
        binding.fab.setOnClickListener {
            val intent = Intent(this@DonaturActivity, KelolaDonaturActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }
            startActivity(intent)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@DonaturActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun attachDonaturRecycler(data_donatur: List<Donatur>) {
        adapterDonatur = DonaturActivityAdapter(data_donatur, object : onClickDonaturAdapter{
            override fun edit(donatur: Donatur) {
                val intent = Intent(this@DonaturActivity, KelolaDonaturActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("DONATUR", donatur)
                }

                startActivity(intent).also {
                    finish()
                }
            }

            override fun delete(donatur: Donatur) {
                delete(donatur.id.toString())
            }

        })

        binding.rvDonatur.apply {
            adapter = adapterDonatur
            layoutManager = LinearLayoutManager(this@DonaturActivity)
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
        presenter?.infoDonatur()
    }

    private fun delete(id : String){
        val token = Constants.getToken(this@DonaturActivity)
        presenter?.delete(token, id)
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