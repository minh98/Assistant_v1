package com.example.minh98.assistant_v1.viewsLayout.ThoiTiet

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.minh98.assistant_v1.R
import com.example.minh98.assistant_v1.viewsLayout.Model
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by minh98 on 10/09/2017.
 */
//data class ItemThoiTiet(val day:String,val resImg:Int,val maxDo:Int,val minDo:Int)

class AdapterThoiTiet(val mContext: Context, data:MutableList<ThoiTiet7Ngay>)
	: RecyclerView.Adapter<AdapterThoiTiet.ViewHolder>() {

	val itemThoiTiet= data
	val model=Model()


	override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
		holder?.tvDoMin?.text= "${itemThoiTiet?.get(position).tempMin.toString()}°"
		holder?.tvDoMax?.text="${itemThoiTiet?.get(position).tempMax.toString()}°"
		Picasso.with(mContext)
				.load("http://openweathermap.org/img/w/${itemThoiTiet?.get(position)?.icon}.png")
				.into(holder?.imgThoiTiet)
		holder?.tvNgay?.text=model.dayOfWeek(Date(itemThoiTiet?.get(position).ngay*1000)).toString()
	}

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
		val inflater=LayoutInflater.from(parent?.context)
		val view=inflater.inflate(R.layout.item_thoi_tiet,parent,false)
		return ViewHolder(view)
	}

	override fun getItemCount(): Int {
		 return itemThoiTiet.size
	}

	inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
		val tvNgay:TextView
		val imgThoiTiet:ImageView
		val tvDoMax:TextView
		val tvDoMin:TextView
		init {
			tvNgay= itemView!!.findViewById(R.id.tv_ngay)
			imgThoiTiet=itemView!!.findViewById(R.id.img_thoi_tiet)
			tvDoMax=itemView!!.findViewById(R.id.tv_do_max)
			tvDoMin=itemView!!.findViewById(R.id.tv_do_min)
		}
	}

}