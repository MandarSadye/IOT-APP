package com.example.mandar.hackerthon_app.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mandar.hackerthon_app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DataUpdateActivity : AppCompatActivity() {

    var firebaseAuth : FirebaseAuth? = null
    var textViewUserEmail : TextView? = null
    var buttonLogout : Button? = null
    var buttonSave : Button? = null
    var editTextName : EditText? = null
    var editTextAdderess : EditText? = null

    var databaseReference : DatabaseReference? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_update)
        var userInformation = SignUpActivity.userInformation

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        if(firebaseAuth!!.currentUser == null){
            startActivity(Intent(this, StartUpActivity::class.java))
            finish()
        }
        var useri : FirebaseUser? = firebaseAuth!!.currentUser


            var user = firebaseAuth!!.currentUser
            databaseReference!!.child(user!!.uid).setValue(userInformation)
            Toast.makeText(this,"Information Saved .... ", Toast.LENGTH_LONG).show()
            StartUpActivity.activity!!.finish()
            startActivity(Intent(this,BaseActivity::class.java))
            finish()

    }

}
