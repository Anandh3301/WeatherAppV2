package com.capgemini.weatherappp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_city_picker.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CityPicker.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityPicker : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val RECORD_REQUEST_CODE = 101
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            askForPermission()

        }
    }

    private fun askForPermission() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city_picker, container, false)
        permissiongranted.visibility= View.INVISIBLE

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.submitbtn).setOnClickListener {
            Toast.makeText(activity,"ok",Toast.LENGTH_LONG).show()
        //starting list activity
            val listfrag = DayFragment()
            val bundel=Bundle()
            when(spinner.selectedItemPosition)
            {
                0 -> {//Toast.makeText(context,spinner.selectedItem.toString(),Toast.LENGTH_SHORT).show()

                    val main = activity as MainActivity?
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(main)

                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        makeRequest()
                    }
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location : Location? ->
                            bundel.putString("lat", location?.latitude.toString()?:"0.0")
                            bundel.putString("lng",location?.longitude.toString()?:"0.0")

                    sendIt(bundel)
                        }

                }
                1 ->{//bangalore
                    bundel.putString("lat","12.9716")
                    bundel.putString("lng","77.5946")
                    sendIt(bundel)
                }
                2-> {//chennai
                    bundel.putString("lat","13.0827")
                    bundel.putString("lng","80.2707")
                    sendIt(bundel)
                }
                3->{//pune
                    bundel.putString("lat","18.5204")
                    bundel.putString("lng","73.8567")

                    sendIt(bundel)
                }
                4->{//greenwich
                    bundel.putString("lat","51.4934")
                    bundel.putString("lng","0.0098")

                    sendIt(bundel)
                }
                5->{//sochi
                    bundel.putString("lat","43.6028")
                    bundel.putString("lng","39.7342")
                    sendIt(bundel)
                }


            }



        }


    }

    fun sendIt(bundle : Bundle)
    {   val listfrag = DayFragment()
        listfrag.arguments=bundle
      /*  activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.parentL,listfrag)

            ?.addToBackStack(null)
            ?.commit()*/
        findNavController().navigate(R.id.action_cityPicker_to_dayFragment,bundle)
    }


    private fun makeRequest() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
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
                    permissiongranted.visibility= View.VISIBLE
                    permissiongranted.text="user location obtained.Click to submit"

                }
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CityPicker.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CityPicker().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}