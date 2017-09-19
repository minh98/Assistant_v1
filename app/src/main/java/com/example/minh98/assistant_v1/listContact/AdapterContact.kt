package com.example.minh98.assistant_v1.listContact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.minh98.assistant_v1.R

/**
 * Created by minh98 on 18/09/2017.
 */
data class ItemContact(val name:String,val number:String,val id:String,val lookupkey:String)
class AdapterContact: ArrayAdapter<ItemContact> {
	var mContext: Context?=null
	var id=-1
	var mList:MutableList<ItemContact>?=null

	constructor(context: Context?, resource: Int, objects: MutableList<ItemContact>?) :
			super(context, resource, objects)
	{
		mContext=context
		id=resource
		mList=objects
	}

	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val viewLayout= (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?)
				?.inflate(id,null)
		(viewLayout?.findViewById<TextView>(R.id.tv_name))
				?.text=mList!![position].name
		(viewLayout?.findViewById<TextView>(R.id.tv_number))
				?.text=mList!![position].number

		return viewLayout!!
	}
}