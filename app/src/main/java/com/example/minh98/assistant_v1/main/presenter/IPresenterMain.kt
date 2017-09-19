package com.example.minh98.assistant_v1.main.presenter

import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ItemThoiTiet
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ItemThoiTiet7Ngay
import com.example.minh98.assistant_v1.viewsLayout.TiGiaNgoaiTe.ItemTiGia

/**
 * Created by minh98 on 09/09/2017.
 */
interface IPresenterMain {
	fun onResults(data: String?)
	fun onPartialResults(data: String?)
	fun startAnimationListening()
	fun onError()
	fun updateColorCircle()
	fun showTiGiaNgoaiTe(result: ItemTiGia)
	fun sendThoiTietCurrent(result: ItemThoiTiet)
	fun sendThoiTiet7Ngay(result: ItemThoiTiet7Ngay)
	fun showKqxs(result: MutableList<String>)
	fun handleTranslate(keywordSearch: String, ngonNgu: String)
	fun handleFindDirection(from: String?, to: String?)
	fun handleAlarm(hour: String?, mode: String?=null, min: String?)
	fun handleWifi(mode: String?)
	fun handleSearchMapGanDay(locate: String?)
	fun handleOpenBrowser(url: String?)
	fun handleFindNumberPhone(name: String?)
	fun handleSMS(address: String?=null, smsBody: String?=null)
	fun handleCallUSSD(mode: String?)

	fun onShowTiGiaNgoaiTe()
	fun handleCallTaxi(locate: String?, data: MutableList<MutableList<String>>)

	fun handleYoutube(search: String?=null)

	fun handleMap(locate: String?)
	fun handleCalculator(v1: String, operatorr: String, v2: String)
	fun handleKqxs(mien: String?)
	fun handleFlash(mode: String?)
	fun handleWeather(locate: String?=null)
	fun handleBrightNess(value: String?)
	fun handleSearchWeb(key: String?)
	fun koHieu(data: String)
	fun showReplyResultTranslate(result: String)
	fun updateWindowAfterSetBrightNess(brightness: Int)
	fun stopThinking()
	fun showReplyWifiOn()
	fun showReplyWifiOff()
	fun showReplyCallUSSD(value: String)
	fun showReplyOpenedMap()
	fun showReplySetedAlarm(hour: Int, min: Int)
	fun showReplyOpenedUrl()
	fun showReplyOpenedSms()
	fun showReplyCalled(number: String)
	fun showReplyOpenedYoutube()
	fun showReplyFlashOff()
	fun showReplyFlashOn()
}