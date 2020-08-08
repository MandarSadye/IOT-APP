package com.example.mandar.hackerthon_app.Fragments

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mandar.hackerthon_app.R
import android.provider.SyncStateContract.Helpers.update
import com.example.mandar.hackerthon_app.Activities.BaseActivity
import java.security.MessageDigest
import kotlin.experimental.and
import android.app.Activity
import android.R.string.no
import android.R.string.yes
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import android.widget.*
import kotlinx.android.synthetic.main.dialoge_custom.view.*


class LockSAuthFragment : Fragment() ,View.OnClickListener {

    var k = ""
    var viewi : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewi = inflater.inflate(R.layout.fragment_lock_sauth, container, false)

        var btnStar = viewi!!.findViewById<Button>(R.id.btnStar)
        var btnHash = viewi!!.findViewById<Button>(R.id.btnHash)
        var btn9 = viewi!!.findViewById<Button>(R.id.btn9)
        var btn8 = viewi!!.findViewById<Button>(R.id.btn8)
        var btn7 = viewi!!.findViewById<Button>(R.id.btn7)
        var btn6 = viewi!!.findViewById<Button>(R.id.btn6)
        var btn5 = viewi!!.findViewById<Button>(R.id.btn5)
        var btn4 = viewi!!.findViewById<Button>(R.id.btn4)
        var btn3 = viewi!!.findViewById<Button>(R.id.btn3)
        var btn2 = viewi!!.findViewById<Button>(R.id.btn2)
        var btn1 = viewi!!.findViewById<Button>(R.id.btn1)
        var btn0 = viewi!!.findViewById<Button>(R.id.btn0)

        btn0.setOnClickListener(this)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)
        btnStar.setOnClickListener(this)
        btnHash.setOnClickListener(this)




        return viewi
    }

    fun display(str : String){
        if(str.length == 0)viewi!!.findViewById<TextView>(R.id.passwordFormat).text = "*  *  *  *"
        else if(str.length == 1)viewi!!.findViewById<TextView>(R.id.passwordFormat).text = "*  *  *  " + k[0]
        else if(str.length == 2)viewi!!.findViewById<TextView>(R.id.passwordFormat).text = "*  *  " + k[1] + "  " + k[0]
        else if(str.length == 3)viewi!!.findViewById<TextView>(R.id.passwordFormat).text = "*  " + k[2] + "  "  + k[1] + "  " + k[0]
        else if(str.length == 4){
            viewi!!.findViewById<TextView>(R.id.passwordFormat).text = k[3] + "  " + k[2] + "  "  + k[1] + "  " + k[0]
            verify()
        }

    }
    fun verify(){
        try{
            val password = StringBuilder(k).reverse().toString()

            val md = MessageDigest.getInstance("MD5")
            md.update(password.toByteArray())

            val byteData = md.digest()

            //convert the byte to hex format method 1
            val sb = StringBuffer()
            for (i in byteData.indices) {
                sb.append(Integer.toString((byteData[i].toInt() and 0xff) + 0x100, 16).substring(1))
            }
            var status = false
            if(sb.toString() == BaseActivity.userInfo!!.lockHash)status =true
            if(status){
                val cdd = CustomDialogClass(activity)
                cdd.show()
            }
        }catch (e:Exception){}
    }
    override fun onClick(v: View?) {
        if(k.length >= 4){
            k = k[0].toString() + k[1].toString() + k[2].toString()
        }
        when (v!!.id){
            R.id.btn0->{
                k = "0" + k
            }
            R.id.btn1->{
                k = "1" + k

            }
            R.id.btn2->{
                k = "2" + k

            }
            R.id.btn3->{
                k = "3" + k

            }
            R.id.btn4->{
                k = "4" + k

            }
            R.id.btn5->{
                k = "5" + k

            }
            R.id.btn6->{
                k = "6" + k

            }
            R.id.btn7->{
                k = "7" + k

            }
            R.id.btn8->{
                k = "8" + k

            }
            R.id.btn9->{
                k = "9" + k

            }
            R.id.btnHash,R.id.btnStar->{
                k = "****"
            }

        }
        display(k)
    }

}

class CustomDialogClass : Dialog , android.view.View.OnClickListener{

    var c: Activity? = null
    var k ="****"
    constructor(a: Activity) : super(a) {
        this.c = a
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.changeKey -> {
                findViewById<LinearLayout>(R.id.LLayout).visibility = View.GONE
                findViewById<LinearLayout>(R.id.LLayout1).visibility = View.GONE
                findViewById<Button>(R.id.Confirm).visibility = View.VISIBLE
                findViewById<TableLayout>(R.id.TABLELayout).visibility = View.VISIBLE
                findViewById<TextView>(R.id.passwordFormatD).visibility = View.VISIBLE

            }
            R.id.Done ->{
                dismiss()
            }
        }
        setk(v)


   }

    fun display(str : String){
        if(str.length == 0)findViewById<TextView>(R.id.passwordFormatD).text = "*  *  *  *"
        else if(str.length == 1)findViewById<TextView>(R.id.passwordFormatD).text = "*  *  *  " + k[0]
        else if(str.length == 2)findViewById<TextView>(R.id.passwordFormatD).text = "*  *  " + k[1] + "  " + k[0]
        else if(str.length == 3)findViewById<TextView>(R.id.passwordFormatD).text = "*  " + k[2] + "  "  + k[1] + "  " + k[0]
        else if(str.length == 4){
            findViewById<TextView>(R.id.passwordFormatD).text = k[3] + "  " + k[2] + "  "  + k[1] + "  " + k[0]
        }

    }
    fun verify(){
        try{
            val password = StringBuilder(k).reverse().toString()

            val md = MessageDigest.getInstance("MD5")
            md.update(password.toByteArray())

            val byteData = md.digest()

            //convert the byte to hex format method 1
            val sb = StringBuffer()
            for (i in byteData.indices) {
                sb.append(Integer.toString((byteData[i].toInt() and 0xff) + 0x100, 16).substring(1))
            }
            Toast.makeText(BaseActivity.mContext,sb.toString() + " " + password,Toast.LENGTH_LONG).show()
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("lockHash").setValue(sb.toString())

        }catch (e:Exception){}
    }

    override fun show() {
        super.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialoge_custom)

        var switch : Switch = findViewById(R.id.LockTHeDoor)
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
                BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("lock").setValue(isChecked)
        }
        findViewById<Button>(R.id.changeKey).setOnClickListener(this)
        findViewById<Button>(R.id.Done).setOnClickListener(this)

        var btnStar = findViewById<Button>(R.id.btnStar0)
        var btnHash = findViewById<Button>(R.id.btnHash0)
        var btn9 = findViewById<Button>(R.id.btn90)
        var btn8 = findViewById<Button>(R.id.btn80)
        var btn7 = findViewById<Button>(R.id.btn70)
        var btn6 = findViewById<Button>(R.id.btn60)
        var btn5 = findViewById<Button>(R.id.btn50)
        var btn4 = findViewById<Button>(R.id.btn40)
        var btn3 = findViewById<Button>(R.id.btn30)
        var btn2 = findViewById<Button>(R.id.btn20)
        var btn1 = findViewById<Button>(R.id.btn10)
        var btn0 = findViewById<Button>(R.id.btn00)

        btn0.setOnClickListener(this)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)
        btnStar.setOnClickListener(this)
        btnHash.setOnClickListener(this)
        findViewById<Button>(R.id.Confirm).setOnClickListener(this)


    }

    fun setk(v:View?){
        if(k.length >= 4){
            if(v!!.id != R.id.Confirm)
            k = k[0].toString() + k[1].toString() + k[2].toString()
        }
        when (v!!.id){
            R.id.btn00->{
                k = "0" + k
            }
            R.id.btn10->{
                k = "1" + k

            }
            R.id.btn20->{
                k = "2" + k

            }
            R.id.btn30->{
                k = "3" + k

            }
            R.id.btn40->{
                k = "4" + k

            }
            R.id.btn50->{
                k = "5" + k

            }
            R.id.btn60->{
                k = "6" + k

            }
            R.id.btn70->{
                k = "7" + k

            }
            R.id.btn80->{
                k = "8" + k

            }
            R.id.btn90->{
                k = "9" + k

            }
            R.id.btnHash0,R.id.btnStar0->{
                k = "****"
            }
            R.id.Confirm->{
                verify()
                dismiss()
            }

        }
        display(k)
    }
}
