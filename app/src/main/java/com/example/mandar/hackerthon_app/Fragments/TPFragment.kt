package com.example.mandar.hackerthon_app.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mandar.hackerthon_app.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TPFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class TPFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tp, container, false)
    }

}// Required empty public constructor
