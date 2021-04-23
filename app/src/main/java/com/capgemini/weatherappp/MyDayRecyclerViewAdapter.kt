package com.capgemini.weatherappp

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capgemini.weatherappp.OpenWeatherAPI7Days.Daily
import com.capgemini.weatherappp.dummy.DummyContent.DummyItem
import java.text.SimpleDateFormat
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyDayRecyclerViewAdapter(
    private val values: List<Daily>, private val mcontext: Context, val fchange: (String)-> (Unit)
) : RecyclerView.Adapter<MyDayRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

  /*  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.dt.toString()
        holder.contentView.text = item.weather[0].description
        holder.setClickListener(object : ItemClickListener{
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                Toast.makeText(mcontext,"clicked: ${position}", Toast.LENGTH_LONG).show()
                fchange(position.toString())
            }
        })

       // Toast.makeText(mcontext,"clicked", Toast.LENGTH_LONG).show()
    }*/

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val c = Calendar.getInstance();
        c.setTimeInMillis(item.dt.toLong()*1000);
        val date: Date = c.getTime()
        val format1 = SimpleDateFormat("EEE,dd-MMM")
        //today = format1.format(date)

        // Calendar.DAY_OF_WEEK
        holder.dayinfoView.text =  format1.format(date)
        holder.moreinfoView.text = item.weather[0].description
        holder.maxinfoView.text=(item.temp.max-273).toString().slice(0..3)
        holder.mininfoview.text=(item.temp.min-273).toString().slice(0..3)
        holder.humidityinfoview.text=item.humidity.toString()
        // http://openweathermap.org/img/wn/10d@2x.png
        val imgurl= "https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png"
        Log.d("image",imgurl)
        Glide.with(holder.itemView.context)
            .load(Uri.parse(imgurl))
            .into(holder.iconInfoView)

        holder.setClickListener(object : ItemClickListener{
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                Toast.makeText(mcontext,"clicked: ${position}", Toast.LENGTH_LONG).show()

                fchange(position.toString())
            }
        })

        // Toast.makeText(mcontext,"clicked", Toast.LENGTH_LONG).show()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) , View.OnClickListener{
        val dayinfoView: TextView = view.findViewById(R.id.dayinfo)
        val iconInfoView: ImageView = view.findViewById(R.id.iconinfo)
        val moreinfoView: TextView = view.findViewById(R.id.moreinfo)
        val maxinfoView: TextView = view.findViewById(R.id.maxinfo)
        val mininfoview: TextView = view.findViewById(R.id.mininfo)
        val humidityinfoview: TextView = view.findViewById(R.id.humidityinfo)
        private var clickListener: ItemClickListener? = null
       init {
        //    view.setTa
           view.setOnClickListener(this)
       }
        fun setClickListener(itemClickListener: ItemClickListener) {
            this.clickListener = itemClickListener
        }
        override fun onClick(v: View?) {
            Toast.makeText(mcontext,"clicked", Toast.LENGTH_LONG).show()
            clickListener?.onClick(v, getPosition(), false);
        }


    }

}
interface ItemClickListener {
    fun onClick(
        view: View?,
        position: Int,
        isLongClick: Boolean
    )
}