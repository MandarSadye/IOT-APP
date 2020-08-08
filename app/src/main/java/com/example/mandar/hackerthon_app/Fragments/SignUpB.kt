package com.example.mandar.hackerthon_app.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.Fragment
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.example.mandar.hackerthon_app.Activities.SignUpActivity
import com.example.mandar.hackerthon_app.R
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.fragment_sign_up_b.*


/**
 * A simple [Fragment] subclass.
 * Use the [SignUpB.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpB : Fragment() , View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    var buttonNextB : Button? = null
    var signupage:EditText?=null
    var signupradio:RadioButton?=null
    var radioStatus = true
    var signupsleep :EditText?=null
    var signupweight:EditText?=null
    var signupheight:EditText?=null

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
        val view = inflater!!.inflate(R.layout.fragment_sign_up_b, container, false)
        view.findViewById<Button>(R.id.NextButtonB).setOnClickListener(this)
        signupage = view.findViewById(R.id.SignUpAge)
        signupradio = view.findViewById(R.id.radioButton)
        //radioButton.setChecked(true);
        signupradio!!.setOnClickListener(this)
        signupsleep = view.findViewById(R.id.SignUpSleepTime)
        signupsleep!!.isEnabled = false
        signupsleep!!.inputType = InputType.TYPE_NULL
        signupweight = view.findViewById(R.id.SignUpWeight)
        signupheight = view.findViewById(R.id.SignUpHeight)
        val inputTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                signupsleep!!.setText((s.toString().trim().toInt()*5).toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        }
        signupage!!.addTextChangedListener(inputTextWatcher)


        return view
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.NextButtonB) {
            SignUpActivity.userInformation!!.age = signupage!!.text.toString().trim().toInt()
            SignUpActivity.userInformation!!.sleepDuration = signupsleep!!.text.toString().trim().toFloat()
            SignUpActivity.userInformation!!.weight = signupweight!!.text.toString().trim().toFloat()
            SignUpActivity.userInformation!!.height = signupheight!!.text.toString().trim().toFloat()
            SignUpActivity.myActivity!!.loadNextScreen(2)
        }
        if(v!!.id == R.id.radioButton){
            radioStatus = !radioStatus
            if(radioStatus){
                signupsleep!!.isEnabled = true
                signupsleep!!.inputType = InputType.TYPE_CLASS_NUMBER
            }
            else{
                signupsleep!!.isEnabled = false
                signupsleep!!.inputType = InputType.TYPE_NULL
            }
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpB.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): SignUpB {
            val fragment = SignUpB()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
