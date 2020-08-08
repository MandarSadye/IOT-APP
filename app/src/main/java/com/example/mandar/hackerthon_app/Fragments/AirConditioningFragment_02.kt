package com.example.mandar.hackerthon_app.Fragments


import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener




/**
 * A simple [Fragment] subclass.
 */
class AirConditioningFragment_02 : Fragment() , NumberPicker.OnValueChangeListener{
    var fab : FloatingActionButton? = null
    companion object {
        var min = 100
        var max = 0
        var avg = 0
        var avgNum = 0
        fun updateValues(i:Int) {
            if (i < min) min = i
            if (i > max) max = i
            var temp = avg * avgNum
            temp += i
            avgNum++
            avg = temp / avgNum
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseActivity.mSupportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context,R.color.ACColor)))
        fab = activity.findViewById(R.id.fab)
        BaseActivity.mSupportActionBar!!.title = "Air Conditioning"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_air_conditioning_02, container, false)
        var i =BaseActivity.userInfo!!.tempInput;

        v!!.findViewById<Switch>(R.id.ACSwitch).setOnCheckedChangeListener { buttonView, isChecked ->
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("acOn").setValue(isChecked)
        }

        v!!.findViewById<TextView>(R.id.currentAC).text = i.toString()
        AirConditioningFragment_02.updateValues(i)
        v.findViewById<TextView>(R.id.minAC).text = AirConditioningFragment_02.min.toString()
        v.findViewById<TextView>(R.id.maxAC).text = AirConditioningFragment_02.max.toString()
        v.findViewById<TextView>(R.id.avgAC).text = AirConditioningFragment_02.avg.toString()
        i = BaseActivity.userInfo!!.ac
        if(i<20)
            v.findViewById<TextView>(R.id.recomentAC).text = "Increase"
        else if(i>30)
            v.findViewById<TextView>(R.id.recomentAC).text = "Decrease"
        else
            v.findViewById<TextView>(R.id.recomentAC).text = "OK"


        var np = v!!.findViewById<NumberPicker>(R.id.ACPicker)
        np!!.maxValue = 100
        np!!.minValue = 0
        np!!.wrapSelectorWheel = false
        setNumberPickerTextColor(np!!, ContextCompat.getColor(context,R.color.ACColor))
        np.setOnValueChangedListener(this)
        np!!.value = v!!.findViewById<TextView>(R.id.currentAC).text.toString().toInt()
        return v!!
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
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("ac").setValue(newVal.toString().trim().toInt())
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
        }catch(e:Throwable){}
    }

    override fun onPause() {
        super.onPause()
        try {
            val params : CoordinatorLayout.LayoutParams = fab!!.layoutParams as CoordinatorLayout.LayoutParams
            params.anchorGravity = GravityCompat.END or Gravity.BOTTOM
            params.setMargins(20,20,20,20)
            fab!!.layoutParams = params
        }catch (e:Throwable){}
    }

}// Required empty public constructor
