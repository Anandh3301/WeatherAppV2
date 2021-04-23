package com.capgemini.weatherappp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.capgemini.weatherappp.OpenWeatherAPI7Days.Daily
import kotlinx.android.synthetic.main.fragment_popup.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "day"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Popup.newInstance] factory method to
 * create an instance of this fragment.
 */
class Popup : Fragment() {
    // TODO: Rename and change types of parameters
    private var day: String? = null
    private var param2: String? = null
    lateinit var dayninfo: Daily
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            day = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //view.findViewById<TextView>(R.id.daytest).text=day
        Log.wtf("day", day)
        dayninfo= Chweeks.daily[day?.toInt()!!]
        Log.wtf("day", dayninfo.toString())
        Log.wtf("day", Chweeks.toString())
        //   Log.wtf("day", dayinfo.toString())
        populateViews(dayninfo)
    }


    private fun populateViews(dayinfo: Daily) {
        todayp.text=setDatefromEpoch(dayinfo.dt,"EEE,dd-MMM")

        Log.wtf("day",setDatefromEpoch(dayinfo.dt,"EEE,dd-MMM") )
        sunrisep.text=setDatefromEpoch(dayinfo.sunrise,"HH:mm:ss")
        sunsetp.text=setDatefromEpoch(dayinfo.sunset,"HH:mm:ss")
        maxp.text=(dayinfo.temp.max-273).toString().slice(0..3)
        minp.text=(dayinfo.temp.min-273).toString().slice(0..3)
        feelp.text=(dayinfo.feelsLike.day-273).toString().slice(0..3)
        hp.text=dayinfo.humidity.toString()
        pp.text=dayinfo.pressure.toString()
        descriptp.text=dayinfo.weather[0].description
        //   todayp.text= today

        // https://openweathermap.org/img/wn/10d@4x.png
        val imgurl= "https://openweathermap.org/img/wn/${dayinfo.weather[0].icon}@4x.png"
        Log.d("imagepopup",imgurl)
        Glide.with(this)
                .load(Uri.parse(imgurl))
                .into(imagep)
    }

    private fun setDatefromEpoch(sec: Int,pattern : String): String {
        val c = Calendar.getInstance();
        c.setTimeInMillis(sec.toLong()*1000);
        Log.d("sun","${c.time}")
        val date: Date = c.getTime()
        val format1 = SimpleDateFormat(pattern)

        // Calendar.DAY_OF_WEEK
        return  format1.format(date)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Popup.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Popup().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}