package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.contracts.PetugasActivityContract
import com.adit.poskologistikapp.databinding.ActivityKelolaPetugasBinding
import com.adit.poskologistikapp.models.Petugas
import com.adit.poskologistikapp.models.Posko
import com.adit.poskologistikapp.presenters.KelolaPetugasActivityPresenter
import com.adit.poskologistikapp.utilities.Constants

class KelolaPetugasActivity : AppCompatActivity(), PetugasActivityContract.CreateOrUpdateView {
    private lateinit var binding : ActivityKelolaPetugasBinding
    private var presenter : PetugasActivityContract.CreateOrUpdatePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaPetugasBinding.inflate(layoutInflater)
        presenter = KelolaPetugasActivityPresenter(this)
        supportActionBar?.hide()
        setContentView(binding.root)
        fill()
        doSave()
        binding.etLevel.setText("Petugas")
    }

    override fun attachToSpinner(posko: List<Posko>) {
        var spinnerAdapterPosko = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, posko)
        binding.idPosko.apply {
            adapter = spinnerAdapterPosko
        }

        if(!isNew()){
            for(item in posko.indices){
                if(posko[item].id == getPetugas()?.id_posko){
                    binding.idPosko.setSelection(item)
                }
            }
        }
    }

    override fun setSelectionSpinner(posko: List<Posko>) {
        TODO("Not yet implemented")
    }

    override fun showToast(message: String) {
        Toast.makeText(this@KelolaPetugasActivity, message, Toast.LENGTH_LONG).show()
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

    override fun success() {
        val intent = Intent(this@KelolaPetugasActivity, PetugasActivity::class.java).also{
            finish()
        }
        startActivity(intent)
    }

    private fun fill(){
        if(!isNew()){
            binding.etUsername.setText(getPetugas()?.username)
        }

    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            val token = Constants.getToken(this)
            var username = binding.etUsername.text.toString()
            var password = binding.etPassWord.text.toString()
            var konfirmasi_password = binding.etConfirmPass.text.toString()
            var level = binding.etLevel.text.toString()

            var objectPosko = binding.idPosko.selectedItem as Posko

            var id_posko = objectPosko.id

            if(isNew()){
                presenter?.create(token!!, username, password, konfirmasi_password, level.toString(), id_posko.toString())
            }else{
                presenter?.update(token!!, getPetugas()?.id.toString(), username, password, konfirmasi_password, level.toString(), id_posko.toString())
            }
        }
    }

    private fun isNew() : Boolean = intent.getBooleanExtra("IS_NEW", true)

    private fun getPetugas() : Petugas? = intent.getParcelableExtra("PETUGAS")

    private fun getPosko(){
        presenter?.getPosko()
    }

    override fun onResume() {
        super.onResume()
        getPosko()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }


}