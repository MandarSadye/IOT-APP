package com.example.mandar.hackerthon_app.Activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mandar.hackerthon_app.DataResources.UserInformation
import com.example.mandar.hackerthon_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class LoadInfo : AppCompatActivity() {

    var activityi : Activity? =null
    var databaseReference : DatabaseReference? =null
    var firebaseAuth : FirebaseAuth? = null
    var firebaseDatabase : FirebaseDatabase? = null
    var authListener : FirebaseAuth.AuthStateListener? = null
    var USERID : String? = null
    var ResponseString : String? = null
    companion object {
        var userInfo : UserInformation? = null
        var em : ValueEventListener? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_info)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        firebaseDatabase = FirebaseDatabase.getInstance()
        var user : FirebaseUser? = firebaseAuth!!.currentUser
        USERID = user!!.uid
        activityi = this
        em = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                userInfo = p0!!.child(USERID).getValue(UserInformation::class.java)
                var k = userInfo
                startActivity(Intent(activityi, BaseActivity::class.java))
                databaseReference!!.removeEventListener(em)
                finish()
            }

            override fun onCancelled(p0: DatabaseError?) {

            }
        })
    }
}
