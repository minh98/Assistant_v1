package com.example.minh98.assistant_v1.viewsLayout.KetQuaXoSo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.minh98.assistant_v1.R

/**
 * Created by minh98 on 13/09/2017.
 */
class LayoutXoSo : LinearLayout {

	var mcontext: Context? = null
	lateinit var layout: View

	constructor(context: Context?) : super(context) {
		init(context)
	}

	constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
		init(context)
	}

	constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
		init(context)
	}

	constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
		init(context)
	}

	private fun init(mcontext: Context?) {
		this.mcontext = mcontext
	}

	fun setData(result: MutableList<String>): LinearLayout {
		layout = LayoutInflater.from(mcontext).inflate(R.layout.layout_ket_qua_xo_so, null)

		f(R.id.tv_title_xo_so).text = result[8]
		f(R.id.tvDacBiet).text = result[0]
		f(R.id.tvNhat).text = result[1]
		f(R.id.tvNhi).text = result[2]
		f(R.id.tvBa).text = result[3]
		f(R.id.tvTu).text = result[4]
		f(R.id.tvNam).text = result[5]
		f(R.id.tvSau).text = result[6]
		f(R.id.tvBay).text = result[7]
		addView(layout)
		return this
	}

	private fun f(id: Int): TextView = layout.findViewById<TextView>(id)


}