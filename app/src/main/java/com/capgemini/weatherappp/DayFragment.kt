package com.capgemini.weatherappp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.capgemini.weatherappp.OpenWeatherAPI7Days.Daily
import com.capgemini.weatherappp.OpenWeatherAPI7Days.SSevenDays
import com.capgemini.weatherappp.OpenWeatherApi.OWInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * A fragment representing a list of Items.
 */
private const val ARG_PARAM1 = "lat"
private const val ARG_PARAM2 = "lng"

class DayFragment : Fragment()
{  lateinit var daylst : SSevenDays
    private var lat: String? = null
    private var lng: String? = null
    lateinit var rview : RecyclerView
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
            lat = it.getString(ARG_PARAM1,"51.4934")
            lng = it.getString(ARG_PARAM2,"0.0098")
            Log.d("bundle","${lat}:${lng}")

            val key = resources.getString(R.string.API_KEY)
            // val request = OWInterface.getInstance().getWeather("12.9716", "77.5946", key, "40")
            val request = OWInterface.getInstance().getDailyWeather(lat=lat!!,lon= lng!!,key=key)
            request.enqueue(OpenWeatherCallbackSeven())


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                rview=view
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
              /*  adapter = MyDayRecyclerViewAdapter(daylst.daily,view.context){
                    val popfrag = Popup()
                    val bundle = Bundle()
                    bundle.putString("day",it)
                    Log.d("lambda",it)
                    popfrag.arguments=bundle
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.parentL,popfrag)

                        ?.addToBackStack(null)
                        ?.commit()
                }*/
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            DayFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    inner class OpenWeatherCallbackSeven : Callback<SSevenDays> {
        override fun onFailure(call: Call<SSevenDays>, t: Throwable) {
            Log.d("succ","fail : ${t.message}")
        }

        override fun onResponse(
            call: Call<SSevenDays>,
            response: Response<SSevenDays>
        ) {
            if(response.isSuccessful)
            {   val weeks=response.body() as SSevenDays

                Chweeks = weeks
                Log.d("realslimshady",response.raw().toString())
                Log.d("realslimshady",weeks.toString())
                weeks.daily.forEach {
                    it.dt
                    val c = Calendar.getInstance();
                    c.setTimeInMillis(it.dt.toLong()*1000);
                    Log.d("times","${c.time}")
                }
                rview.adapter = MyDayRecyclerViewAdapter(weeks.daily,rview.context){
                    val popfrag = Popup()
                    val bundle = Bundle()
                    bundle.putString("day",it)
                    Log.d("lambda",it)
                    popfrag.arguments=bundle
                /*    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.parentL,popfrag)

                        ?.addToBackStack(null)
                        ?.commit()*/
                    findNavController().navigate(R.id.action_dayFragment_to_popup,bundle)
                }

            }


        }
    }
}
