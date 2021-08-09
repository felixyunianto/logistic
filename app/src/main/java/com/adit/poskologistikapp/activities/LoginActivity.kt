package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.adit.poskologistikapp.contracts.LoginActivityContract
import com.adit.poskologistikapp.databinding.ActivityLoginBinding
import com.adit.poskologistikapp.models.User
import com.adit.poskologistikapp.presenters.LoginActivityPresenter
import com.adit.poskologistikapp.utilities.Constants
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class LoginActivity : AppCompatActivity(), LoginActivityContract.LoginActivityView{

    private lateinit var binding : ActivityLoginBinding
    private var presenter : LoginActivityContract.LoginActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        presenter = LoginActivityPresenter(this)
        setContentView(binding.root)
        doLogin()
        checkAuthentication()
    }

    override fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun successLogin(user : User) {
        val deviceToken = Constants.getDeviceToken(this)
        presenter?.saveDeviceToken("Bearer "+user.token, deviceToken)
        val intentLogin = Intent(this@LoginActivity, MainActivity::class.java)

        var gson = Gson()
        var json : String = gson.toJson(user)
        Constants.setList(this, json)

        startActivity(intentLogin).also {
            finish()
        }
    }

    override fun showLoading() {
        binding.loadingLogin.apply {
            visibility = View.VISIBLE
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        binding.loadingLogin.apply {
            visibility = View.GONE
            isIndeterminate = false
            progress = 0
        }
    }

    private fun doLogin(){
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            presenter?.login(username, password, this@LoginActivity)
        }
    }

    private fun checkAuthentication(){
        val token = Constants.getToken(this@LoginActivity)
        if(token != "UNDEFINED"){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java)).also { finish() }
        }
    }
}