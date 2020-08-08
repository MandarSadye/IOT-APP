package com.example.mandar.hackerthon_app.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.mandar.hackerthon_app.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : Activity() , View.OnClickListener {

    var buttonSignIn : Button? = null
    var editTextEmail : EditText? = null
    var editTextPassword : EditText? = null
    var layout : LinearLayout? = null
    var firebaseAuth : FirebaseAuth? = null
    companion object {
        @JvmStatic
        var myActivity : Activity? = null
        var myContext : Context? = null
        var progressbar : ProgressBar? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        firebaseAuth = FirebaseAuth.getInstance()
        myActivity = this
        myContext = this
        if(firebaseAuth!!.currentUser != null){

            myContext!!.startActivity(Intent(myContext, LoadInfo::class.java))
            myActivity!!.finish()
        }

        progressbar = ProgressBar(this,null,android.R.attr.progressBarStyleLarge)
        buttonSignIn = findViewById<Button>(R.id.logInButton)
        editTextEmail = findViewById<EditText>(R.id.logInTextEmailAddtess)
        editTextPassword = findViewById<EditText>(R.id.logInTextPassword)
        layout = findViewById<LinearLayout>(R.id.layout)
        buttonSignIn!!.setOnClickListener(this)

    }

    fun LoginUser(){
        var email = editTextEmail!!.text.toString()
        var password = editTextPassword!!.text.toString()

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_LONG).show()
            return
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show()
            return
        }

        val params = RelativeLayout.LayoutParams(100, 100)
        params.setMargins(10,10,10,10)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout!!.addView(progressbar, params)
        progressbar!!.setVisibility(View.VISIBLE)  //To show ProgressBar
        firebaseAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, test1())
//        progressbar!!.setVisibility(View.GONE)


    }

    override fun onClick(v: View?) {
        LoginUser()
    }
}

class test1 : OnCompleteListener<AuthResult> {
    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful()){
            LogInActivity.myContext!!.startActivity(Intent(LogInActivity.myContext, BaseActivity::class.java))
            LogInActivity.myActivity!!.finish()
        }
        else{
            Toast.makeText(LogInActivity.myContext,"Something went wrong", Toast.LENGTH_LONG).show()
            LogInActivity.progressbar!!.setVisibility(View.GONE)

        }
    }
}
