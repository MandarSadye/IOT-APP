package com.example.mandar.hackerthon_app.Fragments


import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mandar.hackerthon_app.Activities.BaseActivity

import com.example.mandar.hackerthon_app.R
import kotlinx.android.synthetic.main.fragment_lights_01.*
import yuku.ambilwarna.AmbilWarnaDialog
import kotlin.math.abs
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener




/**
 * A simple [Fragment] subclass.
 */
class LightsFragment_01 : Fragment() , NumberPicker.OnValueChangeListener ,View.OnClickListener{

    var fab : FloatingActionButton? = null
    var mbutton : Button?= null
    var lightIndice = 0
    companion object {
        var mDefaultColor : Int? = 0
        var r = 255
        var g = 0
        var b = 0
        var np : NumberPicker? = null
        var np2 : NumberPicker? =null
        var np1:NumberPicker? = null
        var avgNum = 0
        fun getHexadecimal() : String {
            var a = b
            a += (g*256)
            a += (r*256*256)
            return "#" + Integer.toHexString(a).toUpperCase()
        }
        fun getHexadecimalNum() : Int {
            var a = b
            a += (g*256)
            a += (r*256*256)
            return a
        }
        fun updatUICP(i : Int){
            var co = abs(i)
            co += 1
            co = 16777215 - co
            var a : Int = co/256
            b = co - (a*256)
            r = a/256
            g= a - (r*256)
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("lightR").setValue(r)
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("lightG").setValue(g)
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("lightB").setValue(b)
            np!!.value = r
            np1!!.value = g
            np2!!.value = b

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseActivity.mSupportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context,R.color.FanColor)))
        BaseActivity.mSupportActionBar!!.title = "Lighting"
        fab = activity.findViewById(R.id.fab)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_lights_01, container, false)

        view.findViewById<TextView>(R.id.codeImg).text = getHexadecimal()
        view.findViewById<ImageView>(R.id.smallImg).setBackgroundColor(Color.argb(255, r, g, b))
        view.findViewById<ConstraintLayout>(R.id.layoutColor).setBackgroundColor(Color.argb(255, r, g, b))
        view.findViewById<ConstraintLayout>(R.id.REDback).setBackgroundColor(Color.argb(255, r,0,0))
        view.findViewById<ConstraintLayout>(R.id.Greenback).setBackgroundColor(Color.argb(255, 0,g,0))
        view.findViewById<ConstraintLayout>(R.id.BLUEback).setBackgroundColor(Color.argb(255, 0,0, b))
        BaseActivity.mSupportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.argb(255,r,g,b)))

        view.findViewById<Switch>(R.id.LightSwitch).setOnCheckedChangeListener { buttonView, isChecked ->
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("lightOn").setValue(isChecked)
        }

        np = view!!.findViewById<NumberPicker>(R.id.REDPicker)
        np!!.maxValue = 255
        np!!.minValue = 0
        np!!.wrapSelectorWheel = false
        setNumberPickerTextColor(np!!, ContextCompat.getColor(context,R.color.WhiteColor))
        np!!.setOnValueChangedListener(this)
        np!!.value = BaseActivity.userInfo!!.lightR;

        np1 = view!!.findViewById<NumberPicker>(R.id.GREENPicker)
        np1!!.maxValue = 255
        np1!!.minValue = 0
        np1!!.wrapSelectorWheel = false
        setNumberPickerTextColor(np1!!, ContextCompat.getColor(context,R.color.WhiteColor))
        np1!!.setOnValueChangedListener(this)
        np1!!.value = BaseActivity.userInfo!!.lightG;

        np2 = view!!.findViewById<NumberPicker>(R.id.BLUEPicker)
        np2!!.maxValue = 255
        np2!!.minValue = 0
        np2!!.wrapSelectorWheel = false
        setNumberPickerTextColor(np2!!, ContextCompat.getColor(context,R.color.WhiteColor))
        np2!!.setOnValueChangedListener(this)
        np2!!.value = BaseActivity.userInfo!!.lightB;

        mbutton = view.findViewById(R.id.button)
        mbutton!!.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        openColorPicker()
    }

    fun openColorPicker(){
        var pickAColor : AmbilWarnaDialog = AmbilWarnaDialog(BaseActivity.mContext,mDefaultColor!!,testv())
        pickAColor.show()
    }

    fun setNumberPickerTextColor(numberPicker: NumberPicker, color: Int): Boolean {
        val count = numberPicker.childCount
        for (i in 0 until count) {
            val child = numberPicker.getChildAt(i)
            if (child is EditText) {
                try {
                    val selectorWheelPaintField = numberPicker.javaClass
                            .getDeclaredField("mSelectorWheelPaint")
                    selectorWheelPaintField.isAccessible = true
                    (selectorWheelPaintField.get(numberPicker) as Paint).setColor(color)
                    child.setTextColor(color)
                    numberPicker.invalidate()
                    return true
                } catch (e: NoSuchFieldException) {
                    Log.w("setNumberPickerTextColor", e)
                } catch (e: IllegalAccessException) {
                    Log.w("setNumberPickerTextColor", e)
                } catch (e: IllegalArgumentException) {
                    Log.w("setNumberPickerTextColor", e)
                }

            }
        }
        return false
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        try {
            if(picker!!.id == R.id.REDPicker)
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("lightR").setValue(newVal.toString().trim().toInt())
            else if(picker!!.id == R.id.GREENPicker)
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("lightG").setValue(newVal.toString().trim().toInt())
            else if(picker!!.id == R.id.BLUEPicker)
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("lightB").setValue(newVal.toString().trim().toInt())
            BaseActivity.updateRequests()

        }
        catch (e:Throwable){}
    }

    override fun onResume() {
        super.onResume()
        try {
            val params : CoordinatorLayout.LayoutParams = fab!!.layoutParams as CoordinatorLayout.LayoutParams
            params.anchorGravity = GravityCompat.END or Gravity.TOP
            params.setMargins(20,330,20,20)
            fab!!.layoutParams = params
            fab!!.visibility = View.INVISIBLE
        }catch(e:Throwable){}
    }

    override fun onPause() {
        super.onPause()
        try {
            val params : CoordinatorLayout.LayoutParams = fab!!.layoutParams as CoordinatorLayout.LayoutParams
            params.anchorGravity = GravityCompat.END or Gravity.BOTTOM
            params.setMargins(20,20,20,20)
            fab!!.layoutParams = params
            fab!!.visibility = View.VISIBLE
        }catch (e:Throwable){}
    }

}// Required empty public constructor

class testv : AmbilWarnaDialog.OnAmbilWarnaListener{
    override fun onCancel(dialog: AmbilWarnaDialog?) {

    }

    override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
        LightsFragment_01.mDefaultColor = color
        LightsFragment_01.updatUICP(color)
    }
}
