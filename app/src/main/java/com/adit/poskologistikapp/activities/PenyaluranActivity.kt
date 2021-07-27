package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.adapters.PenyaluranAdapter
import com.adit.poskologistikapp.contracts.PenyaluranContract
import com.adit.poskologistikapp.databinding.ActivityPenyaluranBinding
import com.adit.poskologistikapp.models.Penyaluran
import com.adit.poskologistikapp.presenters.PenyaluranPresenter
import com.adit.poskologistikapp.utilities.Constants

class PenyaluranActivity : AppCompatActivity(), PenyaluranContract.View {
    private lateinit var binding : ActivityPenyaluranBinding
    private var presenter : PenyaluranContract.presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenyaluranBinding.inflate(layoutInflater)
        presenter = PenyaluranPresenter(this)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.fab.setOnClickListener{
            val intent = Intent(this@PenyaluranActivity, KelolaPenyaluranActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }

            startActivity(intent)
        }
    }

    override fun attachToRecycler(penyaluran: List<Penyaluran>) {
        binding.rvPetugas.apply {
            adapter = PenyaluranAdapter(penyaluran)
            layoutManager = LinearLayoutManager(this@PenyaluranActivity)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@PenyaluranActivity, message, Toast.LENGTH_LONG).show()
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
        val token = Constants.getToken(this)
        presenter?.getLogistikProduk(token)
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