package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.adapters.KebutuhanAdapter
import com.adit.poskologistikapp.adapters.onClickAdapterKebutuhan
import com.adit.poskologistikapp.contracts.KebutuhanActivityContract
import com.adit.poskologistikapp.databinding.ActivityKebutuhanBinding
import com.adit.poskologistikapp.models.Kebutuhan
import com.adit.poskologistikapp.models.User
import com.adit.poskologistikapp.presenters.KebutuhanPresenter
import com.adit.poskologistikapp.utilities.APIClient
import com.adit.poskologistikapp.utilities.Constants
import com.google.gson.Gson

class KebutuhanActivity : AppCompatActivity(), KebutuhanActivityContract.KebutuhanLogistikActivityView {
    private lateinit var binding : ActivityKebutuhanBinding
    private var presenter : KebutuhanActivityContract.KebutuhanLogistikPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKebutuhanBinding.inflate(layoutInflater)
        presenter = KebutuhanPresenter(this)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.fab.setOnClickListener {
            val intent = Intent(this@KebutuhanActivity, KelolaKebutuhanActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }
            startActivity(intent)
        }

        showHideFab()
    }

    override fun showToast(message: String) {
        Toast.makeText(this@KebutuhanActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun attachKebutuhanLogistikRecycler(kebutuhan_logistik: List<Kebutuhan>) {
        binding.rvLogistikKeluar.apply {
            adapter = KebutuhanAdapter(kebutuhan_logistik,this@KebutuhanActivity, object : onClickAdapterKebutuhan{
                override fun edit(kebutuhan: Kebutuhan) {
                    val intent = Intent(this@KebutuhanActivity, KelolaKebutuhanActivity::class.java).apply {
                        putExtra("IS_NEW", false)
                        putExtra("KEBUTUHAN", kebutuhan)
                    }

                    startActivity(intent)
                }

                override fun delete(kebutuhan: Kebutuhan) {
                    val token = Constants.getToken(this@KebutuhanActivity)
                    presenter?.delete(token, kebutuhan.id.toString())
                }

            })
            layoutManager = LinearLayoutManager(this@KebutuhanActivity)
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

    private fun getIdPosko() : String? = intent.getStringExtra("ID_POSKO")

    private fun showHideFab(){
        val list = Constants.getList(this)
        val user = Gson().fromJson(list, User::class.java)
        val token = Constants.getToken(this)
        if(token == "UNDEFINED"){
            binding.fab.visibility = View.GONE
        }else{
            if(user.id_posko != getIdPosko()){
                binding.fab.visibility = View.GONE
            }
        }
    }

    private fun getData(){
        val token = Constants.getToken(this@KebutuhanActivity)
        getIdPosko()?.let { presenter?.infoKebutuhanLogistik(token, it) }
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