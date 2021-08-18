package com.adit.poskologistikapp.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
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
import okio.IOException

class LocationPickerActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private lateinit var binding : ActivityLocationPickerBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var myLocation : Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var searchView : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        searchLocation()
        mapFragment.getMapAsync({
            googleMap = it
            googleMap?.setOnMapClickListener(this@LocationPickerActivity)
        })

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@LocationPickerActivity)
        getLocation()
    }

    private fun searchLocation() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                var location : String = binding.searchView.query.toString()

                var addressList : List<Address>? = null

                if(location != null || location.equals("")){
                    var geocoder = Geocoder(this@LocationPickerActivity)
                    try {
                        addressList = geocoder.getFromLocationName(location, 1)
                    }catch (e : IOException){
                        e.printStackTrace()
                    }

                    var address : Address = addressList!!.get(0)

                    var latLng = LatLng(address.latitude, address.longitude)

                    googleMap.clear()
                    googleMap.addMarker(MarkerOptions().position(latLng).title(location))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
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
        showAlertDialog("Apakah anda yakin?", p0)
    }

    private fun showAlertDialog(message: String, latLng : LatLng){
        AlertDialog.Builder(this@LocationPickerActivity).apply {
            setMessage(message)
            setPositiveButton("OK"){ d, _ ->
                val returnIntent = Intent(this@LocationPickerActivity, KelolaPoskoActivity::class.java).apply {
                    putExtra("LATITUDE", latLng.latitude.toString())
                    putExtra("LONGITUDE", latLng.longitude.toString())
                }
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
            setNegativeButton("Tidak"){d, _ ->
                d.cancel()
            }
        }.show()
    }
}