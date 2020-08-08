package com.example.mandar.hackerthon_app.Fragments


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mandar.hackerthon_app.Activities.BaseActivity

import com.example.mandar.hackerthon_app.R


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseActivity.mSupportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context,R.color.HomeColor)))
        BaseActivity.mSupportActionBar!!.title = "Home"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
var v = inflater.inflate(R.layout.fragment_home, container, false)
//        v.findViewById<TextView>(R.id.home_lat).text = String.format("%.3f", BaseActivity.userInfo!!.latitude!!)
//        v.findViewById<TextView>(R.id.home_long).text = String.format("%.3f", BaseActivity.userInfo!!.longitude!!)
//        v.findViewById<TextView>(R.id.home_red).text = BaseActivity.userInfo!!.lightR!!.toString()
//        v.findViewById<TextView>(R.id.home_green).text = BaseActivity.userInfo!!.lightG!!.toString()
//        v.findViewById<TextView>(R.id.home_blue).text = BaseActivity.userInfo!!.lightB!!.toString()
//        v.findViewById<TextView>(R.id.home_ac).text = BaseActivity.userInfo!!.ac!!.toString()
//        v.findViewById<TextView>(R.id.home_fan).text = BaseActivity.userInfo!!.fan!!.toString()
//        v.findViewById<TextView>(R.id.home_currTemp).text = BaseActivity.userInfo!!.tempInput!!.toString()
//        v.findViewById<TextView>(R.id.home_temp).text = BaseActivity.userInfo!!.temperature!!.toString()
//        var i = BaseActivity.userInfo!!.monitoringData!!.toString().trim().split(" ")
//        var sum = 0f
//        for(j in i){
//            sum+=j.toInt()
//        }
//        sum = sum/100
//        v.findViewById<TextView>(R.id.home_monitor).text = sum.toString()
        return v
    }

}// Required empty public constructor
