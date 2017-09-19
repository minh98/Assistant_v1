package com.example.minh98.assistant_v1.viewsLayout.ThoiTiet

/**
 * Created by minh98 on 12/09/2017.
 */
data class ThoiTiet7Ngay (val ngay: Long, val icon:String, val tempMax: Int, val tempMin: Int)
class ItemThoiTiet7Ngay{
	var items= mutableListOf<ThoiTiet7Ngay>()
}