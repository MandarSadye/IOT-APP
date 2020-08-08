package com.example.mandar.hackerthon_app.Fragments


import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mandar.hackerthon_app.Activities.BaseActivity
import com.example.mandar.hackerthon_app.Activities.MyBaseLocationListener

import com.example.mandar.hackerthon_app.R
import com.google.android.gms.maps.SupportMapFragment


/**
 * A simple [Fragment] subclass.
 * Use the [MAP_T_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class       MAP_T_Fragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
        BaseActivity.mSupportActionBar!!.title = "Your Location"
        BaseActivity.mSupportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context,R.color.LocationColor)))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_map__t_, container, false)

        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(BaseActivity.mActivity)



        return view
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
         * @return A new instance of fragment MAP_T_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): MAP_T_Fragment {
            val fragment = MAP_T_Fragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
