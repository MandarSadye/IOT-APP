package com.example.mandar.hackerthon_app.Activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.mandar.hackerthon_app.R
import com.google.firebase.auth.FirebaseAuth

class StartUpActivity : Activity() ,View.OnClickListener {

    var firebaseAuth : FirebaseAuth? = null

    companion object {
        var activity : StartUpActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_up)

        firebaseAuth = FirebaseAuth.getInstance()
        if(firebaseAuth!!.currentUser != null){
            startActivity(Intent(this, LoadInfo::class.java))
            finish()
        }

        val buttonLogin : Button? = findViewById<Button>(R.id.buttonSignIn)
        val buttonSignup : Button? = findViewById<Button>(R.id.buttonSignUp)
        buttonLogin!!.setOnClickListener(this)
        buttonSignup!!.setOnClickListener(this)
        activity = this

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.buttonSignUp->{
                startActivity(Intent(this, SignUpActivity::class.java))
            }
            R.id.buttonSignIn->{
                startActivity(Intent(this, LogInActivity::class.java))
            }
            else->null

        }
    }
}
