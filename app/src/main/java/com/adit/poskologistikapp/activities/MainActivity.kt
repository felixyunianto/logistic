package com.adit.poskologistikapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.Fragment
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.databinding.ActivityMainBinding
import com.adit.poskologistikapp.fragments.*
import com.adit.poskologistikapp.utilities.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)//will hide the title
        supportActionBar?.hide();
        setContentView(binding.root)
        moveFragment()
    }

    private fun getToken() : String = Constants.getToken(this@MainActivity)
    private fun getLevel() : String = Constants.getLevel(this@MainActivity)

    private fun moveFragment(){
        if(getToken() == "UNDEFINED"){
            setFragment(HomeFragment())
        }else{
            if(getLevel() == "Admin"){
                setFragment(HomeAdminFragment())
            }else{
                setFragment(HomePetugasFragment())
            }
        }
        binding.bottomNavigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.menu_home -> {
                        if(getToken() == "UNDEFINED"){
                            setFragment(HomeFragment())
                        }else{
                            if(getLevel() == "Admin"){
                                setFragment(HomeAdminFragment())
                            }else{
                                setFragment(HomePetugasFragment())
                            }
                        }
                    }
                R.id.menu_notifikasi -> {
                    setFragment(PemberitahuanFragment())
                }
                R.id.menu_profil -> {
                    setFragment(ProfileFragment())
                }
            }
            true
        }
    }

    private fun setFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }


}