package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.adapters.LogistikMasukActivityAdapter
import com.adit.poskologistikapp.adapters.onClickMasukAdapter
import com.adit.poskologistikapp.contracts.LogistikMasukActivityContract
import com.adit.poskologistikapp.databinding.ActivityLogistikMasukBinding
import com.adit.poskologistikapp.models.LogistikMasuk
import com.adit.poskologistikapp.presenters.LogistikMasukActivityPresenter
import com.adit.poskologistikapp.utilities.Constants
import java.time.LocalDateTime

class LogistikMasukActivity : AppCompatActivity(), LogistikMasukActivityContract.LogistikMasukActivityView {
    private lateinit var binding : ActivityLogistikMasukBinding
    private lateinit var adapterMasuk : LogistikMasukActivityAdapter
    private var presenter : LogistikMasukActivityContract.LogistikMasukActivityPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLogistikMasukBinding.inflate(layoutInflater)
        presenter = LogistikMasukActivityPresenter(this)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val intent = Intent(this@LogistikMasukActivity, KelolaLogistikMasukActivity::class.java).apply {
                putExtra("IS_NEW", true)
                putExtra("KONDISI", "baru")
            }

            startActivity(intent).also {
                finish();
            }
        }
    }

    override fun attachToRecycler(logistik_masuk: List<LogistikMasuk>) {
        adapterMasuk = LogistikMasukActivityAdapter(logistik_masuk, object : onClickMasukAdapter{
            override fun edit(logistik_masuk: LogistikMasuk) {
                val intent = Intent(this@LogistikMasukActivity, KelolaLogistikMasukActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("MASUK", logistik_masuk)
                    putExtra("KONDISI", "lama")
                }

                startActivity(intent).also {
                    finish();
                }
            }

            override fun delete(logistik_masuk: LogistikMasuk) {
                delete(logistik_masuk.id)
            }

        })

        binding.rvLogistikMasuk.apply {
            adapter = adapterMasuk
            layoutManager = LinearLayoutManager(this@LogistikMasukActivity)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@LogistikMasukActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loading.isIndeterminate = true
    }

    private fun delete(id : String){
        val token = Constants.getToken(this)
        presenter?.delete(token, id)
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
        presenter?.getLogistikMasuk(token)
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