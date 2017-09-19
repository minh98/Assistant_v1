package com.example.minh98.assistant_v1.viewsLayout

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.example.minh98.assistant_v1.R

/**
 * Created by minh98 on 08/09/2017.
 */

class MyText : AppCompatTextView {
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
		setBackgroundResource(R.drawable.bg_my_text)
		setPadding(16,8,16,8)
		val layoutParam:LinearLayout.LayoutParams=(LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT))
		layoutParam.gravity=Gravity.RIGHT
		setTextColor(Color.WHITE)
		layoutParam.setMargins(80,8,8,8)

		layoutParams=layoutParam
	}

	fun setText(s:String): TextView {
		super.setText(s)
		return this
	}
}
