package com.example.minh98.assistant_v1.viewsLayout.TiGiaNgoaiTe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.minh98.assistant_v1.R
import com.example.minh98.assistant_v1.viewsLayout.Model

/**
 * Created by minh98 on 11/09/2017.
 */


class AdapterTiGia(context: Context?, resource: Int, objects: MutableList<ItemTiGiaNgoaiTe>) : ArrayAdapter<ItemTiGiaNgoaiTe>(context, resource, objects) {
	var mContext: Context? = context
	var id = resource
	var mList: MutableList<ItemTiGiaNgoaiTe> = objects
	private val model = Model()

	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val viewLayout = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
				as LayoutInflater?)?.inflate(id, null)
		(viewLayout?.findViewById<TextView>(R.id.tv_ma))
				?.text = (mList[position].type)

		(viewLayout?.findViewById<TextView>(R.id.tv_mua_vao))
				?.text = model.customFormat((mList[position].muatienmat).toFloat())

		(viewLayout?.findViewById<TextView>(R.id.tv_chung_khoan))
				?.text =model.customFormat((mList[position].muack).toFloat())

		(viewLayout?.findViewById<TextView>(R.id.tv_ban_ra))
				?.text = model.customFormat((mList[position].bantienmat).toFloat())

		return viewLayout!!
	}
}