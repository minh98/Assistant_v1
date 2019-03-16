package com.example.minh98.assistant_v1.viewsLayout.ThoiTiet

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.minh98.assistant_v1.R
import com.example.minh98.assistant_v1.viewsLayout.Model
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by minh98 on 08/09/2017.
 */
class LayoutThoiTiet :LinearLayout {

	private lateinit var itemThoiTiet:ItemThoiTiet
	private lateinit var itemThoiTiet7Ngay:ItemThoiTiet7Ngay
	lateinit var layout: View
	lateinit var list:RecyclerView
	lateinit var tvDoAm:TextView
	lateinit var tvNhietDo:TextView
	lateinit var tvTenDiaPhuong:TextView
	lateinit var tvThoiGian:TextView
	lateinit var tvTinhTrang:TextView
	lateinit var tvVanTocGio:TextView
	lateinit var imgThoiTiet:ImageView
	var mcontext:Context?=null
	private val model=Model()



	constructor(context: Context?) : super(context){
		init(context)
	}

	constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
		init(context)
	}

	constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
		init(context)
	}

	@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
	constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes){
		init(context)
	}


	fun setData(itemtt:ItemThoiTiet?,itemtt7n:ItemThoiTiet7Ngay?):LinearLayout{
		itemThoiTiet= itemtt!!
		itemThoiTiet7Ngay= itemtt7n!!
		//
		tvTenDiaPhuong.text="${itemThoiTiet?.name},${ itemThoiTiet?.sys?.country }"
		tvThoiGian.text="${model.dateToString(Date(itemThoiTiet?.dt*1000))}"//model.dayOfWeek(Date(itemThoiTiet?.dt*1000))
		Picasso.with(mcontext).load("http://openweathermap.org/img/w/${itemThoiTiet.weather[0].icon}.png").into(imgThoiTiet)
		tvNhietDo.text=Math.round(itemThoiTiet.main?.temp!!).toString()
		tvTinhTrang.text= itemThoiTiet.weather[0].description
		tvDoAm.text="Do am:${itemThoiTiet?.main?.humidity}%"
		tvVanTocGio.text="Van toc gio:${itemThoiTiet?.wind?.speed}m/s"
		val adapter=AdapterThoiTiet(mcontext!!, itemThoiTiet7Ngay.items)
		list.adapter=adapter

		addView(layout)
		return this
	}
	private fun init(mContext:Context?){
		this.mcontext=mContext
 		layout=LayoutInflater.from(mContext).inflate(R.layout.layout_thoi_tiet,null)
		list=layout.findViewById(R.id.list_thoi_tiet)
		tvTenDiaPhuong=layout.findViewById(R.id.tvTenDiaPhuong)

		tvThoiGian=layout.findViewById(R.id.tvThoiGian)

		imgThoiTiet=layout.findViewById(R.id.img_thoi_tiet)
		tvNhietDo=layout.findViewById(R.id.tv_nhiet_do)

		tvTinhTrang=layout.findViewById(R.id.tv_tinh_trang_thoi_tiet)

		tvDoAm=layout.findViewById(R.id.tv_do_am)

		tvVanTocGio= layout.findViewById(R.id.tv_van_toc_gio)


		list.setHasFixedSize(true)

		val layoutmanagerList=LinearLayoutManager(context)

		layoutmanagerList.orientation=LinearLayoutManager.HORIZONTAL

		list.layoutManager =layoutmanagerList
	}
}