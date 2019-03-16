package com.example.minh98.assistant_v1.listCommand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.minh98.assistant_v1.R

/**
 * Created by minh98 on 09/09/2017.
 */

data class ItemCommand(val resId:Int,val name:String)
class AdapterCommandList(context: Context?, resource: Int, objects: MutableList<ItemCommand>?) : ArrayAdapter<ItemCommand>(context, resource, objects) {
	var mContext:Context?= context
	var id= resource
	var mList:MutableList<ItemCommand>?= objects

	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val viewLayout= (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?)
				?.inflate(id,null)
		(viewLayout?.findViewById<ImageView>(R.id.imgCommand))
				?.setBackgroundResource(mList!![position].resId)
		(viewLayout?.findViewById<TextView>(R.id.tvCommand))
				?.text=mList!![position].name

		return viewLayout!!
	}
}


