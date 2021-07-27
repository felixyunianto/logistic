package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.adapters.PoskoActivityAdapter
import com.adit.poskologistikapp.adapters.onClickPoskoAdapter
import com.adit.poskologistikapp.contracts.PoskoActivityContract
import com.adit.poskologistikapp.databinding.ActivityPoskoBinding
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.presenters.PoskoActivityPresenter
import com.adit.poskologistikapp.utilities.Constants

class PoskoActivity : AppCompatActivity(), PoskoActivityContract.PoskoActivityView {
    private lateinit var binding : ActivityPoskoBinding
    private lateinit var adapterPosko : PoskoActivityAdapter
    private var presenter : PoskoActivityContract.PoskoActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoskoBinding.inflate(layoutInflater)
        presenter = PoskoActivityPresenter(this)
        supportActionBar?.hide();
        setContentView(binding.root)

        binding.fab.setOnClickListener{
            val intent = Intent(this@PoskoActivity, KelolaPoskoActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }

            startActivity(intent)
        }
    }

    override fun attachToRecycler(posko: List<Posko>) {
        adapterPosko = PoskoActivityAdapter(posko, object : onClickPoskoAdapter{
            override fun edit(posko: Posko) {
                val intent = Intent(this@PoskoActivity, KelolaPoskoActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("POSKO", posko)
                }

                startActivity(intent)
            }

            override fun delete(posko: Posko) {
                val token = Constants.getToken(this@PoskoActivity)
                presenter?.delete(token, posko.id)
            }

        })
        binding.rvPosko.apply {
            adapter = adapterPosko
            layoutManager = LinearLayoutManager(this@PoskoActivity)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@PoskoActivity, message, Toast.LENGTH_LONG).show()
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