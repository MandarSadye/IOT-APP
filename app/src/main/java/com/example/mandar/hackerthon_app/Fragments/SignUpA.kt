package com.example.mandar.hackerthon_app.Fragments


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.Fragment
import android.content.Intent
import android.text.TextUtils
import android.widget.*
import com.example.mandar.hackerthon_app.Activities.SignUpActivity


import com.example.mandar.hackerthon_app.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 * Use the [SignUpA.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpA : Fragment() ,View.OnClickListener{

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    var editTextEmail : EditText? = null
    var editTextPassword : EditText? = null
    var passwordConfirm : EditText? = null
    var layout : LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        vie = inflater!!.inflate(R.layout.fragment_sign_up, container, false)
        val view : View = vie!!
        SignUpActivity.progressbar = ProgressBar(SignUpActivity.myContext,null,android.R.attr.progressBarStyleLarge)

        editTextEmail = view.findViewById<EditText>(R.id.SignUpTextEmailAddtess)
        editTextPassword = view.findViewById(R.id.SignUpTextPassword)
        layout = view.findViewById<LinearLayout>(R.id.signUpAlayout)
        passwordConfirm = view.findViewById(R.id.SignUpConfirmPassword)
        view.findViewById<Button>(R.id.buttonNextA).setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        var email = editTextEmail!!.text.toString()
        var password = editTextPassword!!.text.toString()
        var confirm = passwordConfirm!!.text.toString()
        if(TextUtils.isEmpty(email)){
            Toast.makeText(SignUpActivity.myContext,"Please Enter Email", Toast.LENGTH_LONG).show()
            return
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(SignUpActivity.myContext,"Please Enter Password", Toast.LENGTH_LONG).show()
            return
        }
        if(password != confirm){
            Toast.makeText(SignUpActivity.myContext,"Re-enterd password is not same as original", Toast.LENGTH_LONG).show()
            return
        }

        val params = RelativeLayout.LayoutParams(100, 100)
        params.setMargins(10,10,10,10)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout!!.addView(SignUpActivity.progressbar, params)
        SignUpActivity.progressbar!!.setVisibility(View.VISIBLE)  //To show ProgressBar
        SignUpActivity.firebaseAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.myActivity as Activity, test())
//        SignUpActivity.firebaseAuth!!.createUserWithEmailAndPassword(email,password).addOnFailureListener(SignUpActivity.myActivity as Activity, testi())
//        progressbar!!.setVisibility(View.GONE)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        var vie : View? =null
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpA.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): SignUpA {
            val fragment = SignUpA()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor

class test : OnCompleteListener<AuthResult> {
    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful()){
            SignUpActivity.myActivity!!.loadNextScreen(1)
        }
        else{
            //Toast.makeText(SignUpActivity.myContext,"Your not Registered", Toast.LENGTH_LONG).show()
            try {
                throw task.exception as Throwable
            }
            catch (e:Throwable){
                Toast.makeText(SignUpActivity.myContext,e.toString(),Toast.LENGTH_LONG).show()
            }
        }
        SignUpActivity.progressbar!!.setVisibility(View.GONE)
    }
}

//class testi : OnFailureListener {
//    override fun onFailure(p0: Exception) {
//        Toast.makeText(SignUpActivity.myContext,p0.toString(),Toast.LENGTH_LONG)
//    }
//}
