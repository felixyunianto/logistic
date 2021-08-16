package com.adit.poskologistikapp.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Transformations.map
import com.adit.poskologistikapp.R
import com.adit.poskologistikapp.databinding.ActivityLocationPickerBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationPickerActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private lateinit var binding : ActivityLocationPickerBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var myLocation : Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync({
            googleMap = it
            googleMap?.setOnMapClickListener(this@LocationPickerActivity)
        })

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@LocationPickerActivity)
        getLocation()
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        if(ActivityCompat.checkSelfPermission(
                this@LocationPickerActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@LocationPickerActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }

        googleMap!!.isMyLocationEnabled = true
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location : Location? ->
            location?.let{ currentLocation ->
                val myCurrentLocation = LatLng(currentLocation.latitude, currentLocation.longitude)
                googleMap.addMarker(MarkerOptions().position(myCurrentLocation).title("Location"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLocation, 15f));

            }
        }
    }


    override fun onMapClick(p0: LatLng) {
        val marker = MarkerOptions().position(LatLng(p0!!.latitude, p0.longitude))
        googleMap.clear()
        googleMap.addMarker(marker)

        println("PICK LAT " + p0.latitude)
        println("PICK LONG " + p0.longitude)
        val returnIntent = Intent(this@LocationPickerActivity, KelolaPoskoActivity::class.java).apply {
            putExtra("LATITUDE", p0.latitude.toString())
            putExtra("LONGITUDE", p0.longitude.toString())
        }
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}