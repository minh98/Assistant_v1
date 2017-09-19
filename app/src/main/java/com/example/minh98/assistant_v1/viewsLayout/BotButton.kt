package com.example.minh98.assistant_v1.viewsLayout

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.example.minh98.assistant_v1.R

/**
 * Created by minh98 on 18/09/2017.
 */
class BotButton :AppCompatTextView{
	constructor(context: Context) : super(context) {
		init()
	}


	constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
		init()
	}

	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
		init()
	}


	private fun init() {
		setBackgroundResource(R.drawable.bg_my_button)
		setPadding(8,8,8,8)
		val layoutParam: LinearLayout.LayoutParams=(LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
		layoutParam.setMargins(8,8,8,8)
		textSize=8.0f
		setAllCaps(false)
		setTextColor(Color.WHITE)
		layoutParams=layoutParam
	}

	fun setData(url:String):TextView{
		val url="https://www.google.com/search?q=$url"
		val iten:Intent= Intent(Intent.ACTION_VIEW, Uri.parse(url))
		setOnClickListener { context.startActivity(iten) }
		return this
	}



	fun setText(s:String): BotButton {
		super.setText(s)
		return this
	}
}