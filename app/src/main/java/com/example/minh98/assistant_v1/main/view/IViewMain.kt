package com.example.minh98.assistant_v1.main.view

import com.example.minh98.assistant_v1.listContact.ItemContact
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ItemThoiTiet
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ItemThoiTiet7Ngay
import com.example.minh98.assistant_v1.viewsLayout.TiGiaNgoaiTe.ItemTiGia

/**
 * Created by minh98 on 09/09/2017.
 */
interface IViewMain {
	fun addTextRequestToLayout(data: String?)
	fun addTiGiaToLayout(result: ItemTiGia)
	fun setItemThoiTiet(result: ItemThoiTiet)
	fun getItemThoiTiet7Ngay(): ItemThoiTiet7Ngay?
	fun addThoiTietToLayout()
	fun setItemThoiTiet7Ngay(result: ItemThoiTiet7Ngay)
	fun getItemThoiTiet(): ItemThoiTiet?
	fun showReplyNotKnowLanguage(keywordSearch: String, ngonNgu: String)
	fun showNoResultContact()
	fun showContacts(numberPhone: MutableList<ItemContact>)
	fun showKoTheChia0()
	fun showResultCalculator(kq: Float, s: String)
	fun showCantCalculator(pheptinh: String)
	fun showNoResultKqxsFor(mien: String?)
	fun showKoHieu(data: String)
	fun showResultTranslate(result: String)
	fun updateWindowAfterSetBrightNess(brightness: Int)
	fun showReplyAfterSetBrightNess(brightness: Int)
	fun showReplyOpenedMap()
	fun showReplySetedAlarm(hour: Int, min: Int)
	fun showReplyWifiOn()
	fun showReplyWifiOff()
	fun showReplyOpenedUrl()
	fun showReplyOpenedSms()
	fun showReplyCallUSSD(value: String)
	fun showReplyCalled(number: String)
	fun showReplyOpenedYoutube()
	fun showReplyFlashOff()
	fun showReplyFlashOn()
	fun sendToTurnSingleLed(isOn: Boolean, index: Int)
	fun sendToTurnMultiLed(isOn: Boolean)
}