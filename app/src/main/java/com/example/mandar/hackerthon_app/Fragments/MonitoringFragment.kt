package com.example.mandar.hackerthon_app.Fragments


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mandar.hackerthon_app.Activities.BaseActivity

import com.example.mandar.hackerthon_app.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.widget.Button
import android.R.attr.button
import android.view.Gravity
import android.R.attr.gravity
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.widget.LinearLayout
import android.support.v4.view.GravityCompat






/**
 * A simple [Fragment] subclass.
 */
class MonitoringFragment : Fragment() {

companion object {
    @JvmStatic
    var viewi : View? =null
    fun plotGraph(view:View,add:Int = 0){
        var graphMonitor : GraphView? = null
        var series : LineGraphSeries<DataPoint>? = null
        graphMonitor = view.findViewById(R.id.graphMonitor)
        var graphMonitor2 : GraphView? = null
        var series2 : LineGraphSeries<DataPoint>? = null
        graphMonitor2 = view.findViewById(R.id.graphMonitor2)
        graphMonitor.removeAllSeries()
        graphMonitor2.removeAllSeries()
        var x = -1
        val a = BaseActivity.userInfo!!.monitoringData!!
        var b = a.trim()
        var c = b.split(" ")
        var yStrings : List<String> = c
        var y = 0
        var sum =0.0
        series = LineGraphSeries()
        series2 = LineGraphSeries()
        for (i in yStrings){
            x ++
            y = i.toInt()
            sum += y
            series!!.appendData(DataPoint(x.toDouble(),y.toDouble()),true,24)
            series2!!.appendData(DataPoint(x.toDouble(),sum),true,24)
        }

        // styling series
        graphMonitor!!.title = "Monitoring your Power usage"
        graphMonitor2!!.title = "Cumulative Usage"
        series!!.color = Color.GREEN
        series2!!.color = Color.RED
        series.isDrawDataPoints = true
        series.setDataPointsRadius(10f)
        series2.isDrawDataPoints = true
        series2.setDataPointsRadius(10f)

// custom paint to make a dotted line
        val paint = Paint()
        paint.setStyle(Paint.Style.STROKE)
        paint.setStrokeWidth(5f)
        paint.setPathEffect(DashPathEffect(floatArrayOf(8f, 5f), 0f))
        paint.color = Color.GREEN
        series.setCustomPaint(paint)
        graphMonitor!!.addSeries(series)

        val paint2 = Paint()
        paint2.setStyle(Paint.Style.STROKE)
        paint2.setStrokeWidth(5f)
        paint2.setPathEffect(DashPathEffect(floatArrayOf(8f, 5f), 0f))
        paint2.color = Color.RED
        series2.setCustomPaint(paint2)
        graphMonitor2!!.addSeries(series2)

// the y bounds are always manual for second scale
        graphMonitor!!.getViewport().setXAxisBoundsManual(true);
        graphMonitor!!.getViewport().setMinX(0.0)
        graphMonitor!!.getViewport().setMaxX(23.0)

        graphMonitor2!!.getViewport().setXAxisBoundsManual(true);
        graphMonitor2!!.getViewport().setMinX(0.0)
        graphMonitor2!!.getViewport().setMaxX(23.0)
    }
}
    var fab : FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseActivity.mSupportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#20A3AB")))
        BaseActivity.mSupportActionBar!!.title = "Monitoring Power"
        fab = activity.findViewById(R.id.fab)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewi = inflater.inflate(R.layout.fragment_monitoring, container, false)
        plotGraph(viewi!!)
        return viewi
    }

    override fun onResume() {
        super.onResume()
        try {
            val params : CoordinatorLayout.LayoutParams = fab!!.layoutParams as CoordinatorLayout.LayoutParams
            params.anchorGravity = GravityCompat.END or Gravity.CENTER
            fab!!.layoutParams = params
        }catch(e:Throwable){}
    }

    override fun onPause() {
        super.onPause()
        try {
            val params : CoordinatorLayout.LayoutParams = fab!!.layoutParams as CoordinatorLayout.LayoutParams
            params.anchorGravity = GravityCompat.END or Gravity.BOTTOM
            fab!!.layoutParams = params
        }catch (e:Throwable){}
    }


}// Required empty public constructor
