package com.adit.poskologistikapp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.adit.poskologistikapp.contracts.PoskoActivityContract
import com.adit.poskologistikapp.databinding.ActivityKelolaPoskoBinding
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.presenters.KelolaPoskoPresenter
import com.adit.poskologistikapp.utilities.Constants

class KelolaPoskoActivity : AppCompatActivity(), PoskoActivityContract.CreateOrUpdateView {
    private lateinit var binding : ActivityKelolaPoskoBinding
    private var presenter : PoskoActivityContract.CreateOrUpdateInteraction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaPoskoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = KelolaPoskoPresenter(this)
        binding.btnLatlong.setOnClickListener {
            val intent = Intent(this@KelolaPoskoActivity, LocationPickerActivity::class.java)
            startActivityForResult(intent, 1)
        }

        doSave()
        fill()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                val lat = data!!.getStringExtra("LATITUDE")
                val long = data!!.getStringExtra("LONGITUDE")

                println("LAT " + lat)
                println("LONG " + long)

                binding.etLat.setText(lat)
                binding.etLong.setText(long)
            }
        }
    }

    override fun showToast(message: String?) {
        Toast.makeText(this@KelolaPoskoActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loadingPosko.apply {
            visibility = View.VISIBLE
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.loadingPosko.apply {
            visibility = View.GONE
            isIndeterminate = false
            progress = 0
        }
    }

    override fun success() {
        val intent = Intent(this@KelolaPoskoActivity, PoskoActivity::class.java).also {
            finish()
        }

        startActivity(intent)

    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getPosko() : Posko? = intent.getParcelableExtra("POSKO")

    private fun fill(){
        if(!isNew()){
            binding.etNama.setText(getPosko()?.nama)
            binding.etJumlah.setText(getPosko()?.jumlah_pengungsi)
            binding.etNoHp.setText(getPosko()?.kontak_hp)
            binding.etLokasi.setText(getPosko()?.lokasi)
            binding.etLat.setText(getPosko()?.latitude)
            binding.etLong.setText(getPosko()?.longitude)
        }
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = Constants.getToken(this)
            val nama = binding.etNama.text.toString()
            val jumlah_pengungsi = binding.etJumlah.text.toString()
            val kontak_hp = binding.etNoHp.text.toString()
            val lokasi = binding.etLokasi.text.toString()
            val latitude = binding.etLat.text.toString()
            val longitude = binding.etLong.text.toString()

            if(isNew()){
                presenter?.create(token!!, nama, jumlah_pengungsi, kontak_hp, lokasi, longitude, latitude)
            }else{
                presenter?.update(token!!, getPosko()!!.id.toString(), nama, jumlah_pengungsi, kontak_hp, lokasi, longitude, latitude)
            }

        }
    }


}