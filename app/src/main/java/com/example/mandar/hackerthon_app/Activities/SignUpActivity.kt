package com.example.mandar.hackerthon_app.Activities

import android.app.Activity
import android.app.FragmentManager
import com.example.mandar.hackerthon_app.R
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.example.mandar.hackerthon_app.DataResources.UserInformation
import com.example.mandar.hackerthon_app.Fragments.SignUpA
import com.example.mandar.hackerthon_app.Fragments.SignUpB
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignUpActivity : Activity() {



    companion object {
        @JvmStatic
        var firebaseAuth : FirebaseAuth? = null
        var myContext : Context? = null
        var myActivity : SignUpActivity? = null
        var progressbar : ProgressBar? = null
        var userInformation : UserInformation? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        userInformation = UserInformation()
        firebaseAuth = FirebaseAuth.getInstance()
        myContext = this
        myActivity = this
        if(firebaseAuth!!.currentUser != null){

            startActivity(Intent(this, LoadInfo::class.java))
            finish()
        }

        var manager : FragmentManager? = fragmentManager
        var fragment : Fragment? = manager!!.findFragmentById(R.id.SignUpReplaceFrame)
        if(fragment == null){
            fragment = SignUpA.newInstance("","")
            manager.beginTransaction().replace(R.id.SignUpReplaceFrame,fragment).commit()
        }


    }


    fun loadNextScreen(a:Int){
        var manager : FragmentManager? = fragmentManager
        when (a){
            1->{
                    var fragment = SignUpB.newInstance("","")
                    manager!!.beginTransaction().replace(R.id.SignUpReplaceFrame,fragment).addToBackStack(null).commit()
            }
            2->{
                startActivity(Intent(this, MapsActivity::class.java))
                null
            }
            else->{

            }
        }
    }
}
