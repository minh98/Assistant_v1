package com.example.minh98.assistant_v1.viewsLayout.TiGiaNgoaiTe

/**
 * Created by minh98 on 11/09/2017.
 */
data class ItemTiGiaNgoaiTe(var type:String, var imageurl:String, var muatienmat:String, var muack:String, var bantienmat:String, var banck:String)
class ItemTiGia {
	var items= mutableListOf<ItemTiGiaNgoaiTe>()
}