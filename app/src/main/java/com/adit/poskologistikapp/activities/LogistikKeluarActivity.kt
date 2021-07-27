package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.adapters.LogistikKeluarActivityAdapter
import com.adit.poskologistikapp.adapters.onClickKeluarAdapter
import com.adit.poskologistikapp.contracts.LogistikKeluarActivityContract
import com.adit.poskologistikapp.databinding.ActivityLogistikKeluarBinding
import com.adit.poskologistikapp.models.LogistikKeluar
import com.adit.poskologistikapp.presenters.LogistikKeluarActivityPresenter
import com.adit.poskologistikapp.utilities.Constants

class LogistikKeluarActivity : AppCompatActivity(), LogistikKeluarActivityContract.LogistikKeluarActivityView {
    private lateinit var binding : ActivityLogistikKeluarBinding
    private lateinit var adapterKeluar : LogistikKeluarActivityAdapter
    private var presenter : LogistikKeluarActivityContract.LogistikKeluarActivityPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogistikKeluarBinding.inflate(layoutInflater)
        presenter = LogistikKeluarActivityPresenter(this)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val intent = Intent(this@LogistikKeluarActivity, KelolaLogistikKeluarActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }

            startActivity(intent).also {
                finish();
            }
        }
    }

    override fun attachToRecycler(logistik_keluar: List<LogistikKeluar>) {
        adapterKeluar = LogistikKeluarActivityAdapter(logistik_keluar, object : onClickKeluarAdapter{
            override fun edit(logistik_keluar: LogistikKeluar) {
                val intent = Intent(this@LogistikKeluarActivity, KelolaLogistikKeluarActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("KELUAR", logistik_keluar)
                }

                startActivity(intent)
            }

            override fun delete(logistik_keluar: LogistikKeluar) {
                delete(logistik_keluar.id)
            }

        })

        binding.rvLogistikKeluar.apply {
            adapter = adapterKeluar
            layoutManager = LinearLayoutManager(this@LogistikKeluarActivity)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@LogistikKeluarActivity, message, Toast.LENGTH_LONG).show()
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
        val token = Constants.getToken(this@LogistikKeluarActivity)
        presenter?.getLogistikKeluar(token)
    }

    private fun delete(id : String){
        val token = Constants.getToken(this@LogistikKeluarActivity)
        presenter?.hapusLogistikKeluar(token, id)
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