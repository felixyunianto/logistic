package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.KelolaBencanaActivity
import com.adit.poskologistikapp.adapters.BencanaActivityAdapter
import com.adit.poskologistikapp.adapters.onClickBencanaAdapter
import com.adit.poskologistikapp.contracts.BencanaActivityContract
import com.adit.poskologistikapp.databinding.ActivityBencanaBinding
import com.adit.poskologistikapp.models.Bencana
import com.adit.poskologistikapp.presenters.BencanaActivityPresenter
import com.adit.poskologistikapp.utilities.Constants

class BencanaActivity : AppCompatActivity(), BencanaActivityContract.BencanaActivityView {
    private lateinit var binding : ActivityBencanaBinding
    private lateinit var adapterBencana : BencanaActivityAdapter
    private var presenter : BencanaActivityContract.BencanaActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBencanaBinding.inflate(layoutInflater)
        presenter = BencanaActivityPresenter(this)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val intent = Intent(this@BencanaActivity, KelolaBencanaActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }

            startActivity(intent)
        }
    }

    override fun attachToRecycle(bencana: List<Bencana>) {
        adapterBencana = BencanaActivityAdapter(bencana, object : onClickBencanaAdapter {
            override fun edit(bencana: Bencana) {
                val intent = Intent(this@BencanaActivity, KelolaBencanaActivity::class.java).apply{
                    putExtra("IS_NEW", false)
                    putExtra("BENCANA", bencana)
                }
                startActivity(intent).also{
                    finish()
                }
            }

            override fun delete(bencana: Bencana) {
                delete(bencana.id.toString())
            }

        })
        binding.rvBencana.apply {
            adapter = adapterBencana
            layoutManager = LinearLayoutManager(this@BencanaActivity)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@BencanaActivity, message, Toast.LENGTH_LONG).show()
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
        presenter?.infoBencana()
    }

    private fun delete(id : String){
        val token = Constants.getToken(this)
        presenter?.delete(token!!, id)
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