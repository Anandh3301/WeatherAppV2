package com.capgemini.weatherappp
import android.os.Build
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.capgemini.weatherappp.OpenWeatherAPI7Days.SSevenDays
import com.capgemini.weatherappp.OpenWeatherApi.OWInterface
import com.capgemini.weatherappp.OpenWeatherApi.SevenWeatherData
import com.capgemini.weatherappp.OpenWeatherApi.week
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

import android.content.Context

import android.support.v4.*
import android.view.View
import kotlinx.android.synthetic.main.fragment_city_picker.*


class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val TAG = "PermissionDemo"
    private val RECORD_REQUEST_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Log.d("succ", "after call")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.



            // Initialize a new instance of ManagePermissions class
       //     makeRequest()
        }


        //  MyTask().execute()
        Log.d("succ", "before call")
        val frag = CityPicker.newInstance("", "")
        supportFragmentManager.beginTransaction()
            .add(R.id.parentL, frag)
            .commit()
    }
  /*  private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            RECORD_REQUEST_CODE)
    }*/

 /*   override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i("TAG", "Permission has been denied by user")
                } else {
                    Log.i("TAG", "Permission has been granted by user")
                }
            }
        }
    }*/

    inner class OpenWeatherCallback : Callback<SevenWeatherData> {
        override fun onFailure(call: Call<SevenWeatherData>, t: Throwable) {
            Log.d("succ", "fail : ${t.message}")
        }

        override fun onResponse(
            call: Call<SevenWeatherData>,
            response: Response<SevenWeatherData>
        ) {
            if (response.isSuccessful) {
                val weeks = response.body() as SevenWeatherData
                val weeksfiltered = mutableListOf<week>()

                weeks.list.forEachIndexed { index, week ->
                    if (index % 8 == 0 || index == 39) {
                        Log.d("succ", week.toString())
                        weeksfiltered.add(week)
                    }

                }


            }
        }
    }
}


