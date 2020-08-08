package com.example.mandar.hackerthon_app.Activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.mandar.hackerthon_app.Activities.MapsActivity.Companion.latitude
import com.example.mandar.hackerthon_app.Activities.MapsActivity.Companion.longitude
import com.example.mandar.hackerthon_app.Activities.MapsActivity.Companion.mMap
import com.example.mandar.hackerthon_app.Activities.MapsActivity.Companion.marker
import com.example.mandar.hackerthon_app.Activities.SignUpActivity.Companion.userInformation
import com.example.mandar.hackerthon_app.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Math.PI

class MapsActivity : AppCompatActivity(), OnMapReadyCallback , View.OnClickListener {



    var locationmanager : LocationManager? = null
    var listener : MyLocationListener? = null
    var signupName : EditText? = null
    var signupradius : EditText? = null

    companion object {
        lateinit var mMap: GoogleMap
        var a : Context?=null
        var marker : Marker? = null
        var status = true
        var latitude:Double = 0.0
        var longitude:Double = 0.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        listener = MyLocationListener()
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        a=applicationContext
        locationmanager = getSystemService(LOCATION_SERVICE) as LocationManager
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        locationmanager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0f,listener)

        findViewById<Button>(R.id.NextButtonC).setOnClickListener(this)
        findViewById<Button>(R.id.buttonRefresh).setOnClickListener(this)
        signupName = findViewById(R.id.SignUpName)
        signupradius = findViewById(R.id.SignUpRadius)


    }

    override fun onClick(v: View?) {
        when(v!!.id ){
            R.id.buttonRefresh -> status = true
            R.id.NextButtonC -> {
                userInformation!!.longitude = (longitude*PI/180).toFloat()
                userInformation!!.latitude = (latitude*PI/180).toFloat()
                userInformation!!.name = signupName!!.text.toString().trim()
                userInformation!!.monitoringRadius = signupradius!!.text.toString().trim().toFloat()
                startActivity(Intent(this,DataUpdateActivity::class.java))
                SignUpActivity.myActivity!!.finish()
                finish()
            }
            else -> null
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}

class MyLocationListener : LocationListener{
    override fun onLocationChanged(location: Location?) {
            if(marker != null ) marker!!.remove()
            MapsActivity.latitude = location!!.latitude
            MapsActivity.longitude = location!!.longitude
            var latLong : LatLng = LatLng(latitude,longitude)
            var geocoder : Geocoder = Geocoder(MapsActivity.a)
            var addresses : List<Address> = geocoder.getFromLocation(latitude,longitude,1)
            var str = addresses.get(0).locality +" "+ addresses.get(0).countryName
                marker = mMap.addMarker(MarkerOptions().position(latLong).title(str + " " + latitude + " " + longitude))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong,18f))
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}