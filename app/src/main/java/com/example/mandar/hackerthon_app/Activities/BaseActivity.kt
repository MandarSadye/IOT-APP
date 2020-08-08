package com.example.mandar.hackerthon_app.Activities

import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.example.mandar.hackerthon_app.DataResources.UserInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.app_bar_base.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.location.*
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.example.mandar.hackerthon_app.DataResources.MyFirebaseInstanceIdService
import com.example.mandar.hackerthon_app.Fragments.*
import com.example.mandar.hackerthon_app.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener , OnMapReadyCallback,  View.OnClickListener {
    var editTextName : TextView? = null
    var textViewUserEmail : TextView? = null
    var buttonLogout : Button? = null





    /*******/

    var signupName : EditText? = null
    var signupradius : EditText? = null
    companion object {
        var locationmanager : LocationManager? = null
        var listener : MyBaseLocationListener? = null
        var mMap : GoogleMap? = null
        var a : Context?=null
        var marker : Marker? = null
        var markeri : Marker? = null
        var status = true
        var latitude:Double = 0.0
        var longitude:Double = 0.0
        var latitudei:Double = 0.0
        var longitudei:Double = 0.0
        var groundOverlay  : GroundOverlay? = null
        var mActivity : BaseActivity? = null
        var mContext : Context? = null
        var isMapReady = false
        var isMapRecieve = false

        var databaseReference : DatabaseReference? =null
        var firebaseAuth : FirebaseAuth? = null
        var firebaseDatabase : FirebaseDatabase? = null
        var authListener : FirebaseAuth.AuthStateListener? = null
        var USERID : String? = null
        var ResponseString : String? = null
        var userInfo : UserInformation? = null

        var mSupportActionBar : ActionBar ? = null


        var fab : FloatingActionButton? = null

        fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val theta = lon1 - lon2
            var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + (Math.cos(deg2rad(lat1))
                    * Math.cos(deg2rad(lat2))
                    * Math.cos(deg2rad(theta)))
            dist = Math.acos(dist)
            dist = rad2deg(dist)
            dist = dist * 60.0 * 1.1515
            return dist
        }
            private fun deg2rad(deg: Double): Double {
                return deg * Math.PI / 180.0
            }

            private fun rad2deg(rad: Double): Double {
                return rad * 180.0 / Math.PI
            }

        fun updateRequests(){
            var responseStr = BaseActivity.ResponseString
            if(responseStr!!.contains(BaseActivity.USERID!!)){}
            else{
                responseStr = responseStr + " " + BaseActivity.USERID!!
                BaseActivity.databaseReference!!.child("responseRequired").setValue(responseStr)
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userInfo = LoadInfo.userInfo
        var k = userInfo
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)

        mSupportActionBar = supportActionBar
        fab = findViewById(R.id.fab)

        fab!!.setOnClickListener (this)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        firebaseDatabase = FirebaseDatabase.getInstance()

        var user : FirebaseUser? = firebaseAuth!!.currentUser
        mActivity = this
        mContext = this

        USERID = user!!.uid

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        val header = navigationView.getHeaderView(0)

        editTextName = header.findViewById<TextView>(R.id.nav_name)
        textViewUserEmail = header.findViewById<TextView>(R.id.nav_email)
        textViewUserEmail!!.text = user!!.email

        authListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user : FirebaseUser = p0.currentUser!!
                if(user != null){

                }
                else{

                }
            }
        }

        databaseReference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                getData(p0)
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })

        var manager  = supportFragmentManager
        var fragment = manager.findFragmentById(R.id.frameBaseFragment)
        if(fragment == null){
            fragment = HomeFragment()
            manager.beginTransaction().add(R.id.frameBaseFragment,fragment).commit()
        }
        BaseActivity.listener = MyBaseLocationListener()

        BaseActivity.a =applicationContext
        BaseActivity.locationmanager = getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        if(!((ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)||
                (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.INTERNET)== PackageManager.PERMISSION_GRANTED)))return
        BaseActivity.locationmanager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0f, BaseActivity.listener)
//        BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("token").setValue(MyFirebaseInstanceIdService.rrr.toString())



    }

    override fun onMapReady(googleMap: GoogleMap) {
        BaseActivity.mMap = googleMap
        isMapReady =true
        var k = isMapReady
        null
        // Add a marker in Sydney and move the camera

    }

    fun getData(dataSnap : DataSnapshot?){
        BaseActivity.userInfo = dataSnap!!.child(USERID).getValue(UserInformation::class.java)
        ResponseString = dataSnap!!.child("responseRequired").getValue().toString().trim()
        var k =dataSnap!!.child(USERID).child("tempInput").getValue().toString()
        Toast.makeText(BaseActivity.mContext, userInfo!!.tempInput.toString(),Toast.LENGTH_LONG).show()
        try{
            findViewById<TextView>(R.id.home_lat).text = String.format("%.3f", BaseActivity.userInfo!!.latitude!!)
            findViewById<TextView>(R.id.home_long).text = String.format("%.3f", BaseActivity.userInfo!!.longitude!!)
            findViewById<TextView>(R.id.home_red).text = BaseActivity.userInfo!!.lightR!!.toString()
            findViewById<TextView>(R.id.home_green).text = BaseActivity.userInfo!!.lightG!!.toString()
            findViewById<TextView>(R.id.home_blue).text = BaseActivity.userInfo!!.lightB!!.toString()
            findViewById<TextView>(R.id.home_ac).text = BaseActivity.userInfo!!.ac!!.toString()
            findViewById<TextView>(R.id.home_fan).text = BaseActivity.userInfo!!.fan!!.toString()
            findViewById<TextView>(R.id.home_currTemp).text = BaseActivity.userInfo!!.tempInput!!.toString()
            findViewById<TextView>(R.id.home_temp).text = BaseActivity.userInfo!!.temperature!!.toString()
            var i = BaseActivity.userInfo!!.monitoringData!!.toString().trim().split(" ")
            var sum = 0f
            for(j in i){
                sum+=j.toInt()
            }
            sum = sum/100
            findViewById<TextView>(R.id.home_monitor).text = sum.toString()
        }catch (e:Exception){}
        try{
            editTextName!!.text = userInfo!!.name.toString()
        }catch (E:Throwable){}
        try{
            LightsFragment_01.r = userInfo!!.lightR
            LightsFragment_01.g = userInfo!!.lightG
            LightsFragment_01.b = userInfo!!.lightB
            findViewById<TextView>(R.id.codeImg).text = LightsFragment_01.getHexadecimal()
            findViewById<ImageView>(R.id.smallImg).setBackgroundColor(Color.argb(255, LightsFragment_01.r, LightsFragment_01.g, LightsFragment_01.b))
            findViewById<ConstraintLayout>(R.id.layoutColor).setBackgroundColor(Color.argb(255, LightsFragment_01.r, LightsFragment_01.g, LightsFragment_01.b))
            findViewById<ConstraintLayout>(R.id.REDback).setBackgroundColor(Color.argb(255, LightsFragment_01.r,0,0))
            findViewById<ConstraintLayout>(R.id.Greenback).setBackgroundColor(Color.argb(255, 0, LightsFragment_01.g,0))
            findViewById<ConstraintLayout>(R.id.BLUEback).setBackgroundColor(Color.argb(255, 0,0, LightsFragment_01.b))
            BaseActivity.mSupportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.argb(255, LightsFragment_01.r, LightsFragment_01.g, LightsFragment_01.b)))
        }catch (e:Throwable){}
        try{
            FanFragment_03.updateValues(BaseActivity.userInfo!!.fan)
            findViewById<TextView>(R.id.currentFan).text = BaseActivity.userInfo!!.fan.toString()
            findViewById<TextView>(R.id.minFan).text = FanFragment_03.min.toString()
            findViewById<TextView>(R.id.maxFan).text = FanFragment_03.max.toString()
            findViewById<TextView>(R.id.avgFan).text = FanFragment_03.avg.toString()
            if(BaseActivity.userInfo!!.fan<40)
                findViewById<TextView>(R.id.recomentFan).text = "Increase"
            else if(BaseActivity.userInfo!!.fan>50)
                findViewById<TextView>(R.id.recomentFan).text = "Decrease"
            else
                findViewById<TextView>(R.id.recomentFan).text = "OK"

        }catch (e:Throwable){}
        try{
            AirConditioningFragment_02.updateValues(BaseActivity.userInfo!!.tempInput)
            findViewById<TextView>(R.id.currentAC).text = BaseActivity.userInfo!!.tempInput.toString()
            findViewById<TextView>(R.id.minAC).text = AirConditioningFragment_02.min.toString()
            findViewById<TextView>(R.id.maxAC).text = AirConditioningFragment_02.max.toString()
            findViewById<TextView>(R.id.avgAC).text = AirConditioningFragment_02.avg.toString()
            if(BaseActivity.userInfo!!.ac<20)
                findViewById<TextView>(R.id.recomentAC).text = "Increase"
            else if(BaseActivity.userInfo!!.ac>30)
                findViewById<TextView>(R.id.recomentAC).text = "Decrease"
            else
                findViewById<TextView>(R.id.recomentAC).text = "OK"
            findViewById<NumberPicker>(R.id.ACPicker).value = BaseActivity.userInfo!!.ac
        }catch (e:Throwable){}
        try{
            TemperatureFragment_04.updateValues(BaseActivity.userInfo!!.tempInput)
            findViewById<TextView>(R.id.currentTemp).text = BaseActivity.userInfo!!.tempInput.toString()
            findViewById<TextView>(R.id.minTemp).text = TemperatureFragment_04.min.toString()
            findViewById<TextView>(R.id.maxTemp).text = TemperatureFragment_04.max.toString()
            findViewById<TextView>(R.id.avgTemp).text = TemperatureFragment_04.avg.toString()
            if(BaseActivity.userInfo!!.temperature<20)
                findViewById<TextView>(R.id.recomentTemp).text = "Increase"
            else if(BaseActivity.userInfo!!.temperature>30)
                findViewById<TextView>(R.id.recomentTemp).text = "Decrease"
            else
                findViewById<TextView>(R.id.recomentTemp).text = "OK"
            findViewById<NumberPicker>(R.id.TemperaturePicker).value = BaseActivity.userInfo!!.temperature

        }catch (e:Throwable){}
        try{
            MonitoringFragment.plotGraph(MonitoringFragment.viewi!!,1)
        }catch (e:Throwable){}
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth!!.addAuthStateListener(authListener!!)
    }

    override  fun onStop() {
        super.onStop()
        if(authListener != null){
            firebaseAuth!!.removeAuthStateListener(authListener!!)
        }
    }

    fun toastMessage(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fab -> {
                Snackbar.make(v!!, "you are connected", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.base, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Tracking your way", Toast.LENGTH_LONG).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.nav_home -> {
                isMapReady = false
                var manager = supportFragmentManager
                var fragment = HomeFragment()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()

            }
            R.id.nav_timeline -> {
                isMapReady = false
                var manager = supportFragmentManager
                var fragment = MAP_T_Fragment()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()

            }
            R.id.nav_camera -> {
                var manager = supportFragmentManager
                var fragment = LightsFragment_01()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()
            }
            R.id.nav_gallery -> {
                var manager = supportFragmentManager
                var fragment = AirConditioningFragment_02()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()

            }
            R.id.nav_slideshow -> {
                var manager = supportFragmentManager
                var fragment = FanFragment_03()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()

            }
            R.id.nav_manage -> {
                var manager = supportFragmentManager
                var fragment = TemperatureFragment_04()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()

            }
            R.id.nav_monitoring -> {
                isMapReady = false
                var manager = supportFragmentManager
                var fragment = MonitoringFragment()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()

            }
            R.id.nav_lock -> {
                isMapReady = false
                var manager = supportFragmentManager
                var fragment = LockSAuthFragment()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()

            }
            R.id.nav_share -> {
                var manager = supportFragmentManager
                var fragment = TPFragment()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()

            }
            R.id.nav_send -> {
                var manager = supportFragmentManager
                var fragment = TPFragment()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()

            }
            R.id.Feedback -> {
                var manager = supportFragmentManager
                var fragment = TPFragment()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()

            }
            R.id.LOGOUT -> {
                firebaseAuth!!.signOut()
                startActivity(Intent(this, StartUpActivity::class.java))
                finish()

            }
            R.id.SETTINGS -> {
                var manager = supportFragmentManager
                var fragment = TPFragment()
                manager.beginTransaction().replace(R.id.frameBaseFragment,fragment).commit()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}

class MyBaseLocationListener : LocationListener{
    override fun onLocationChanged(location: Location?) {
        if(BaseActivity.mMap != null) {
            if(BaseActivity.isMapReady){
                if(BaseActivity.groundOverlay !=null){
                    BaseActivity.groundOverlay!!.remove()
                }
                if(BaseActivity.markeri !=null){
                    BaseActivity.markeri!!.remove()
                }
                BaseActivity.latitudei = BaseActivity.userInfo!!.latitude!!.toDouble()*180/Math.PI
                BaseActivity.longitudei = BaseActivity.userInfo!!.longitude!!.toDouble()*180/Math.PI
                val HomeLocation = LatLng(BaseActivity.latitudei, BaseActivity.longitudei)
                BaseActivity.markeri = BaseActivity.mMap!!.addMarker(MarkerOptions().position(HomeLocation).title("Home"))
                BaseActivity.mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(HomeLocation,18f))
                BaseActivity.mMap!!.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
                var radius = BaseActivity.userInfo!!.monitoringRadius!! //diameter od a circle
                var d : Int = radius.toInt() * 10
                if(d>1000)d=1000 // diameter of a image
                var bm : Bitmap = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
                var c = Canvas(bm)
                var p = Paint()
                p.color = Color.GREEN
                c.drawCircle(d.toFloat()/2, d.toFloat()/2, d.toFloat()/2, p)
                var bmD = BitmapDescriptorFactory.fromBitmap(bm)
                BaseActivity.groundOverlay = BaseActivity.mMap!!.addGroundOverlay(GroundOverlayOptions().image(bmD).position(HomeLocation,radius*2.toFloat(),radius*2.toFloat()).transparency(0.2f))
                BaseActivity.isMapRecieve = true
            }
            if (BaseActivity.marker != null) BaseActivity.marker!!.remove()
            BaseActivity.latitude = location!!.latitude
            BaseActivity.longitude = location!!.longitude
            var latLong: LatLng = LatLng(BaseActivity.latitude, BaseActivity.longitude)
            var geocoder: Geocoder = Geocoder(BaseActivity.a)
            var str = "Current Location"
            BaseActivity.marker = BaseActivity.mMap!!.addMarker(MarkerOptions().position(latLong).title(str + " " + BaseActivity.latitude + " " + BaseActivity.longitude))
            BaseActivity.mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 18f))
            BaseActivity.mMap!!.setMapType(GoogleMap.MAP_TYPE_SATELLITE)
            try {
                var c = BaseActivity.distance(BaseActivity.latitude, BaseActivity.longitude, BaseActivity.latitudei, BaseActivity.longitudei)*1000
                if (BaseActivity.isMapRecieve) {
                    BaseActivity.mActivity!!.findViewById<TextView>(R.id.distanceView).text = "Distance : " +
                            (c).toInt() + " m"
                    if(c>BaseActivity.userInfo!!.monitoringRadius!!){

                        BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("temperatureOn").setValue(false)
                        BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("fanOn").setValue(false)
                        BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("lightOn").setValue(false)
                        BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("acOn").setValue(false)
                    }

                }
                else
                    BaseActivity.mActivity!!.findViewById<TextView>(R.id.distanceView).text = "Waiting for firebase data"
            }

            catch (e:Throwable){}
            }

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