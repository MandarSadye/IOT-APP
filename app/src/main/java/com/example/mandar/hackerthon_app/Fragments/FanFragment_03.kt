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


/**
 * A simple [Fragment] subclass.
 */
class FanFragment_03 : Fragment() , NumberPicker.OnValueChangeListener{
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
        BaseActivity.mSupportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context,R.color.FanColor)))
        fab = activity.findViewById(R.id.fab)
        BaseActivity.mSupportActionBar!!.title = "Fan Speed"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_fan_fragment_03, container, false)
        var i =BaseActivity.userInfo!!.fan;

        v!!.findViewById<Switch>(R.id.FanSwitch).setOnCheckedChangeListener { buttonView, isChecked ->
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("fanOn").setValue(isChecked)
        }

        v!!.findViewById<TextView>(R.id.currentFan).text = i.toString()
        FanFragment_03.updateValues(i)
        v.findViewById<TextView>(R.id.minFan).text = FanFragment_03.min.toString()
        v.findViewById<TextView>(R.id.maxFan).text = FanFragment_03.max.toString()
        v.findViewById<TextView>(R.id.avgFan).text = FanFragment_03.avg.toString()
        if(i<40)
            v.findViewById<TextView>(R.id.recomentFan).text = "Increase"
        else if(i>50)
            v.findViewById<TextView>(R.id.recomentFan).text = "Decrease"
        else
            v.findViewById<TextView>(R.id.recomentFan).text = "OK"


        var np1 = v!!.findViewById<NumberPicker>(R.id.FanPicker)
        np1!!.maxValue = 100
        np1!!.minValue = 0
        np1!!.wrapSelectorWheel = false
        setNumberPickerTextColor(np1!!, ContextCompat.getColor(context,R.color.FanColor))
        np1.setOnValueChangedListener(this)
        np1!!.value = v!!.findViewById<TextView>(R.id.currentFan).text.toString().toInt()


        var np2 = v!!.findViewById<NumberPicker>(R.id.FanPicker)
        np2!!.maxValue = 100
        np2!!.minValue = 0
        np2!!.wrapSelectorWheel = false
        setNumberPickerTextColor(np2!!, ContextCompat.getColor(context,R.color.FanColor))
        np2.setOnValueChangedListener(this)
        np2!!.value = v!!.findViewById<TextView>(R.id.currentFan).text.toString().toInt()


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
            BaseActivity.databaseReference!!.child(BaseActivity.USERID).child("fan").setValue(newVal.toString().trim().toInt())
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


}
