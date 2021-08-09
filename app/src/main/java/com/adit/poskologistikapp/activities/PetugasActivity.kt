package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskologistikapp.adapters.PetugasActivityAdapter
import com.adit.poskologistikapp.adapters.onClickPetugasAdapter
import com.adit.poskologistikapp.contracts.PetugasActivityContract
import com.adit.poskologistikapp.databinding.ActivityPetugasBinding
import com.adit.poskologistikapp.models.Petugas
import com.adit.poskologistikapp.presenters.PetugasActivityPresenter
import com.adit.poskologistikapp.utilities.Constants

class PetugasActivity : AppCompatActivity(), PetugasActivityContract.PetugasActivityView {
    private lateinit var binding : ActivityPetugasBinding
    private lateinit var adapterPetugas : PetugasActivityAdapter
    private var presenter : PetugasActivityContract.PetugasActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetugasBinding.inflate(layoutInflater)
        presenter = PetugasActivityPresenter(this)
        supportActionBar?.hide()
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            startActivity(Intent(this@PetugasActivity, KelolaPetugasActivity::class.java).apply {
                putExtra("IS_NEW", true)
            }).also {
                finish()
            }
        }

        binding.searchPetugas.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapterPetugas.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterPetugas.filter.filter(newText)
                return false
            }

        })
    }

    override fun showToast(message: String) {
        Toast.makeText(this@PetugasActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun attachPetugasRecycler(petugas: ArrayList<Petugas>) {
        adapterPetugas = PetugasActivityAdapter(petugas, object : onClickPetugasAdapter{
            override fun edit(petugas: Petugas) {
                val intent = Intent(this@PetugasActivity, KelolaPetugasActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("PETUGAS", petugas)
                }

                startActivity(intent).also { finish() }
            }

            override fun delete(petugas: Petugas) {
                delete(petugas.id)
            }

            override fun detail(petugas: Petugas) {
                val intent = Intent(this@PetugasActivity, DetailPetugasActivity::class.java).apply {
                    putExtra("DETAIL_PETUGAS", petugas)
                }

                startActivity(intent)
            }

        })
        binding.rvPetugas.apply {
            adapter = adapterPetugas
            layoutManager = LinearLayoutManager(this@PetugasActivity)
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
        val token = Constants.getToken(this@PetugasActivity)
        presenter?.infoPetugas(token)
    }

    private fun delete(id : String){
        val token = Constants.getToken(this@PetugasActivity)
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