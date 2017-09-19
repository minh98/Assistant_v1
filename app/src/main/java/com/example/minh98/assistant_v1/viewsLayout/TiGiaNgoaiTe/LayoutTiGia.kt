package com.example.minh98.assistant_v1.viewsLayout.TiGiaNgoaiTe

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import com.example.minh98.assistant_v1.R

/**
 * Created by minh98 on 11/09/2017.
 */
class LayoutTiGia: LinearLayout {
	var layout: View?=null
	var list:ListView?=null
	var mContext:Context?=null

	constructor(context: Context?) : super(context){
		init(context)
	}
	constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
		init(context)
	}
	constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
		init(context)
	}
	constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes){
		init(context)
	}

	fun setData(data: ItemTiGia):LinearLayout{
		val adapter=AdapterTiGia(mContext,R.layout.item_ti_gia_ngoai_te,data.items)
		Log.e("size",data.items.size.toString())
		list?.adapter=adapter
		addView(layout)
		return this
	}

	private fun init(mContext: Context?){
		this.mContext=mContext
		layout= LayoutInflater.from(mContext).inflate(R.layout.layout_ti_gia_ngoai_te,null)
		list=layout?.findViewById<ListView>(R.id.list_ngoai_te)
	}
}