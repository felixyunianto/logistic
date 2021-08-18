package com.adit.poskologistikapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.adit.poskologistikapp.databinding.ActivityEditProfileBinding
import com.adit.poskologistikapp.models.Petugas
import com.adit.poskologistikapp.models.User
import com.adit.poskologistikapp.responses.WrappedResponse
import com.adit.poskologistikapp.utilities.APIClient
import com.adit.poskologistikapp.utilities.Constants
import com.adit.poskologistikapp.webservices.APIServices
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        toolbarAction()
        parseToView()
        doSave()
        setContentView(binding.root)
    }

    private fun toolbarAction() {
        val actionBar = supportActionBar
        actionBar!!.title = "Edit Profil"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
        onBackPressed()
        return true
    }

    private fun parseToView() {
        val listUser : User;
        val json = Constants.getList(this)
        listUser = Gson().fromJson(json, User::class.java)

        binding.etuserId.setText(listUser.id)
        binding.etuserId.visibility = View.INVISIBLE
        binding.etPoskoId.setText(listUser.id_posko)
        binding.etPoskoId.visibility = View.INVISIBLE
        binding.etUsername.setText(listUser.username)
        binding.etLevel.setText(listUser.level)
        binding.etLevel.isEnabled = false
    }

    private fun doSave(){
        binding.btnSubmit.setOnClickListener {
            binding.loadingEditprofile.apply {
                isIndeterminate = true
                visibility = View.VISIBLE
            }
            val token = Constants.getToken(this)
            val userId = binding.etuserId.text.toString()
            val username = binding.etUsername.text.toString()
            val level = binding.etLevel.text.toString()
            val password = binding.etPassWord.text.toString()
            val password_confrim = binding.etConfirmPass.text.toString()
            val poskoId = binding.etPoskoId.text.toString()

            if(password != password_confrim){
                Toast.makeText(this@EditProfileActivity, "Password dan konfirmasi tidak sama", Toast.LENGTH_LONG).show()
            }

            val request = APIClient.APIService().updatePetugasPosko(token!!, userId, username, password, password_confrim, level, poskoId)
            request.enqueue(object : Callback<WrappedResponse<Petugas>> {
                override fun onResponse(
                    call: Call<WrappedResponse<Petugas>>,
                    response: Response<WrappedResponse<Petugas>>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            Toast.makeText(this@EditProfileActivity, "Berhasil mengubah profile", Toast.LENGTH_LONG).show()
                            onBackPressed()
                        }
                    }else{
                        var errorBody = JSONObject(response.errorBody()?.string())
                        Toast.makeText(this@EditProfileActivity, errorBody.getString("error"), Toast.LENGTH_LONG).show()
                    }

                    binding.loadingEditprofile.apply {
                        isIndeterminate = false
                        progress = 0
                        visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<WrappedResponse<Petugas>>, t: Throwable) {
                    Toast.makeText(this@EditProfileActivity, "Tidak bisa koneksi ke server", Toast.LENGTH_LONG).show()
                    binding.loadingEditprofile.apply {
                        isIndeterminate = false
                        progress = 0
                        visibility = View.GONE
                    }
                }
            })
        }
    }


}