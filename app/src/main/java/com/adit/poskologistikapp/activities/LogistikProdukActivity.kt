package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.adapters.LogistikProdukActivityAdapter
import com.adit.poskologistikapp.adapters.onClickLogistikAdapter
import com.adit.poskologistikapp.contracts.LogistikProdukActivityContract
import com.adit.poskologistikapp.databinding.ActivityLogistikProdukBinding
import com.adit.poskologistikapp.models.Logistik
import com.adit.poskologistikapp.models.User
import com.adit.poskologistikapp.presenters.LogistikProdukActivityPresenter
import com.adit.poskologistikapp.utilities.Constants
import com.google.gson.Gson

class LogistikProdukActivity : AppCompatActivity(), LogistikProdukActivityContract.LogistikProdukActivityView {
    private lateinit var binding : ActivityLogistikProdukBinding
    private lateinit var adapterLogistik : LogistikProdukActivityAdapter
    private var presenter : LogistikProdukActivityContract.LogistikProdukActivityPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLogistikProdukBinding.inflate(layoutInflater)
        presenter = LogistikProdukActivityPresenter(this)
        setContentView(binding.root)
        showHideFab()

        binding.fab.setOnClickListener {
            val intent = Intent(this@LogistikProdukActivity, KelolaLogistikActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }

            startActivity(intent).also {
                finish();
            }
        }
    }

    override fun attachToRecycler(logistik: List<Logistik>) {
        println("DATA OGISIK " + logistik)
        adapterLogistik = LogistikProdukActivityAdapter(logistik, this@LogistikProdukActivity, object: onClickLogistikAdapter{
            override fun edit(logistik: Logistik) {
                val intent = Intent(this@LogistikProdukActivity, KelolaLogistikActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("LOGISTIK", logistik)
                }

                startActivity(intent).also{
                    finish()
                }
            }

            override fun delete(logistik: Logistik) {
                delete(logistik.id)
            }

        })

        binding.rvProduk.apply {
            adapter = adapterLogistik
            layoutManager = LinearLayoutManager(this@LogistikProdukActivity)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@LogistikProdukActivity, message, Toast.LENGTH_LONG).show()
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

    private fun showHideFab(){
        val list = Constants.getList(this@LogistikProdukActivity)
        var user = Gson().fromJson(list, User::class.java)

        if(user.id_posko != getIdPosko()){
            binding.fab.visibility = View.GONE
        }
    }

    private fun isFromBeranda() : Boolean = intent.getBooleanExtra("IS_FROM_BERANDA", true)

    private fun getIdPosko() : String? = intent.getStringExtra("ID_POSKO")

    private fun getData(){
        val token = Constants.getToken(this@LogistikProdukActivity)

        if(isFromBeranda()){
            presenter?.getLogistikProduk(token)
        }else{
            presenter?.getLogistikProdukByPosko(getIdPosko().toString())
        }
    }

    private fun delete(id : String){
        val token = Constants.getToken(this@LogistikProdukActivity)
        presenter?.deleteLogistik(token, id)
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