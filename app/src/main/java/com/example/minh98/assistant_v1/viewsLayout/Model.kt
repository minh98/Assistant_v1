package com.example.minh98.assistant_v1.viewsLayout

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by minh98 on 13/09/2017.
 */
class Model {
	fun dayOfWeek(date: Date): String {
		val c: Calendar = Calendar.getInstance()
		c.time = date
		val day=c.get(Calendar.DAY_OF_WEEK)
		return when(day){
			1->"CN"
			in 2..7->"T${day}"
			else->""
		}
	}

	fun dateToString(date: Date): String {
		val simple1 = SimpleDateFormat("EEEE dd-MM-yyyy")
		val simple2= SimpleDateFormat("HH:mm:ss")
		return "${simple1.format(date)} Cap nhat luc:${simple2.format(date)}"
	}

	fun customFormat( value: Float):String {
		//return "${value/1000.0}.${value%1000.0}"
		val myFormatter = DecimalFormat("###,###")
		val output = myFormatter.format(value)
		return if(value>999)(output) else ("0,${value.toInt()}")
	}
}