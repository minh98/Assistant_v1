package com.example.minh98.assistant_v1.main.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import com.example.minh98.assistant_v1.R
import com.example.minh98.assistant_v1.listCommand.ItemCommand
import com.example.minh98.assistant_v1.listContact.ItemContact
import com.example.minh98.assistant_v1.main.model.ModelMain
import com.example.minh98.assistant_v1.main.view.IViewMain
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ItemThoiTiet
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ItemThoiTiet7Ngay
import com.example.minh98.assistant_v1.viewsLayout.TiGiaNgoaiTe.ItemTiGia





/**
 * Created by minh98 on 09/09/2017.
 */
class PresenterMain (val mIView:IViewMain,val mContext : Context):IPresenterMain{

	val mModel:ModelMain

	init {
		mModel= ModelMain(mContext,this)
	}

	fun onClickItemCommand(item: ItemCommand) {
		//xu li lenh theo position cua item list
		mIView.addTextRequestToLayout(item.name)
		mModel.handleRequest(item.name)
	}

	fun onClickListen(isListening: Boolean) {
		if(!isListening){
			try{
				mModel.stopListen()
			}catch (e:Exception){
				Log.e("stop listen",e.toString())
			}

		}else {
			try {
				mModel.startListen()
				mIView.startAnimationListening()
			} catch (e: Exception) {
				Log.e("Listening", e.toString())
			}
		}


	}

	override fun startAnimationListening() {
		mIView.startAnimationListening()
	}

	override fun onResults(data: String?) {
		mIView.addTextRequestToLayout(data)
		mIView.setTextListening("Đang suy nghĩ...")
		/**
		 * add request xong se xu li text va thuc hien cac lenh tuong ung
		 */
		mModel.handleRequest(data!!.toLowerCase())
	}

	override fun stopThinking() {
		mIView.startHideAnimCircleListen()
		mIView.hideTextListening()
	}

	override fun onPartialResults(data: String?) {
		mIView.setTextListening(data)
	}

	override fun onError() {
		//reset
//		mIView.stopAnimationListening()
		mIView.startHideAnimCircleListen()
		mIView.hideTextListening()
	}



	fun animOnListeningEnd() {
		mIView.hideBtnListen()
		mIView.startShowAnimCircleListen()
	}

	fun animOffListeningEnd() {
		// sau khi btnlisten hien len
	}

	fun animHideCircleListenEnd() {
		mIView.hideLayoutCircleListen()
		mIView.showBtnListen()
	}

//	fun animShowCircleListenEnd() {
//		//start thread change color of circle
//
//		mModel.runTheadChangeColor()
//		mIView.startAnimationCircleUpDown()
//	}

	override fun updateColorCircle() {
		//update state color (0,1,2)
		var currentState=mIView.getStateCircleColorCurrent()+1

		if (currentState>2){
			currentState=0
		}
		var circle_listening1=0
		var circle_listening2=0
		var circle_listening3=0
		when(currentState){
			0->{
				circle_listening1= R.drawable.circle_listening1
				circle_listening2= R.drawable.circle_listening2
				circle_listening3= R.drawable.circle_listening3
			}
			1->{
				circle_listening1= R.drawable.circle_listening3
				circle_listening2= R.drawable.circle_listening1
				circle_listening3= R.drawable.circle_listening2
			}
			2->{
				circle_listening1= R.drawable.circle_listening2
				circle_listening2= R.drawable.circle_listening3
				circle_listening3= R.drawable.circle_listening1
			}
		}
		Log.e("currentState",currentState.toString())
		mIView.updateStateCircleColor(currentState)
		//color xong
		mIView.updateColorCircle(circle_listening1,circle_listening2,circle_listening3)
		mIView.clearAnimationUpDown()
		mIView.startAnimationCircleUpDown()
	}

	fun animShowCircleListenEnd() {
		mIView.startAnimationCircleUpDown()
		mIView.showTextListening()
	}

	override fun showTiGiaNgoaiTe(result: ItemTiGia) {
		mIView.addTiGiaToLayout(result)
	}

	fun loadTiGiaNgoaiTe() {
		mModel.loadTiGiaNgoaiTe()
	}

	fun loadThoiTiet() {
		mModel.loadThoiTiet("thainguyen")
	}

	override fun sendThoiTietCurrent(result: ItemThoiTiet) {
		mIView.setItemThoiTiet(result)
		if(mIView.getItemThoiTiet7Ngay()!=null){
			//da du du lieu
			mIView.addThoiTietToLayout()
		}
	}

	override fun sendThoiTiet7Ngay(result: ItemThoiTiet7Ngay) {
		mIView.setItemThoiTiet7Ngay(result)
		if(mIView.getItemThoiTiet()!=null){
			//da du du lieu
			mIView.addThoiTietToLayout()
		}
	}

	override fun showKqxs(result: MutableList<String>) {
		mIView.showKqxs(result)
	}

	fun onStop() {
		mModel.onStop()
	}

	fun onPause() {
		mModel.speechDestroy()
	}

	fun onStart() {
		mModel.speechInit()
	}

	/**
	 * handle commands
	 */

	override fun handleTranslate(keywordSearch: String, ngonNgu: String) {
		Log.e("command","handleTranslate")
		stopThinking()
		val lagtarget=when(ngonNgu){
			"anh"->"en"
			"pháp"->"fr"
			"hàn quốc"->"ko"
			"nhật"->"ja"
			else->""
		}
		if(lagtarget.isNotEmpty()) {
			mModel.loadTranslate("vi", lagtarget, keywordSearch)
		}else{
			mIView.showReplyNotKnowLanguage(keywordSearch,ngonNgu)
		}


	}

	override fun handleFindDirection(from: String?, to: String?) {
		Log.e("command","handlefind direction")
		stopThinking()
		mModel.onOpenMap(from=from,to=to)
	}

	override fun showReplyOpenedMap() {
		mIView.showReplyOpenedMap()
	}

	override fun handleAlarm(hour: String?, mode: String?, min: String?) {
		Log.e("command","handle alarm")
		stopThinking()
		val mmin=if(min?.isNotEmpty()!!)min?.trim()?.toInt() else 0
		var mhour=0
		mhour = if(mode=="giờ kém"){
			if(hour?.trim()?.toInt()!!<1) {
				23
			}else{
				hour.trim().toInt() -1
			}
		}else{
			hour?.trim()?.toInt()!!
		}
		mModel.onSetAlarm(mhour, mmin!!)

	}

	override fun showReplySetedAlarm(hour: Int, min: Int) {
		mIView.showReplySetedAlarm(hour=hour,min=min)
	}

	override fun handleWifi(mode: String?) {

		Log.e("command","handle wifi")
		stopThinking()
		when(mode){
			"bật"->mModel.turnOnWifi()
			"tắt"->mModel.turnOffWifi()
		}
	}

	override fun showReplyWifiOn() {
		mIView.showReplyWifiOn()
	}

	override fun showReplyWifiOff() {
		mIView.showReplyWifiOff()
	}

	override fun handleSearchMapGanDay(locate: String?) {
		Log.e("command","handle search map dia diem(vd: nha hang gan day) gan day")
		stopThinking()
		mModel.onOpenMap(locate = locate)
	}

	override fun handleOpenBrowser(url: String?) {
		Log.e("command","handle open browser")
		stopThinking()
		//vd vao trang tinh te
		var ins=false
		mModel.getUrlMap().map { if(it[0]==url){
			ins=true
			mModel.onOpenBrowser(url=it[1])
			return
		} }
		mModel.onOpenBrowser(url="https://www.google.com/search?q=${Uri.encode(url)}")
	}

	override fun showReplyOpenedUrl() {
		mIView.showReplyOpenedUrl()
	}

	override fun handleFindNumberPhone(name: String?) {
		Log.e("command","handle find number phone")
		//tim kiem so dien thoai trong danh ba
		stopThinking()
		val nums=mModel.getNumberPhone(name!!)
		if(nums.size>0){
			mIView.showContacts(nums)
		}else{
			mIView.showNoResultContact()
		}
	}

	override fun handleSMS(address: String?, smsBody: String?) {
		Log.e("command","handle sms")
		/**co 2 truong hop
		 * 1. khong co noi dung
		 * 2.co noi dung
		 */
		//1
		stopThinking()
		if(address!=null&&smsBody!=null) {
			mModel.onOpenSms(address, smsBody)
		}else{
			mModel.onOpenSms()
		}
	}

	override fun showReplyOpenedSms() {
		mIView.showReplyOpenedSms()
	}

	override fun handleCallUSSD(mode: String?) {
		Log.e("command","handle call ussd")
		stopThinking()
		val mmode=mode?.trim()
		// gốc| phụ| khuyến mãi| khuyến mại| chính
		if(mmode?.isNotEmpty()!! || mmode!=null) {
			when (mmode) {
				"chính", "gốc" -> mModel.onCallUSSD("*101#")
				else -> mModel.onCallUSSD("*102#")
			}
		}else{
			mModel.onCallUSSD("*101#")
			//mac dinh se la tai khoan chinh
		}
	}

	override fun showReplyCallUSSD(value: String) {
		mIView.showReplyCallUSSD(value=value)
	}

	override fun onShowTiGiaNgoaiTe() {
		Log.e("command","handle show ti gia ngoai te")
		stopThinking()
		mModel.loadTiGiaNgoaiTe()
	}

	override fun handleCallTaxi(locate: String?, data: MutableList<MutableList<String>>) {
		Log.e("command","handle call taxi")
		stopThinking()
		val mdata=data.filter { it.contains(locate) }
		val number=(mdata[0][1]as String).replace(" ","")
		mModel.onCall(number)
	}

	override fun showReplyCalled(number: String) {
		mIView.showReplyCalled(number=number)
	}

	override fun handleYoutube(search: String?) {
		Log.e("command","handle youtube")
		stopThinking()
		mModel.onOpenYoutube(search)
	}

	override fun showReplyOpenedYoutube() {
		mIView.showReplyOpenedYoutube()
	}

	override fun handleMap(locate: String?) {
		Log.e("command","handle map")
		stopThinking()
		mModel.onOpenMap(locate = locate)
	}

	override fun handleCalculator(v1: String, operatorr: String, v2: String) {
		Log.e("command","handle calculator")
		//cộng|trừ|nhận|chia
		stopThinking()
		if((operatorr=="chia"||operatorr=="/")&&v2.toInt()==0){
			mIView.showKoTheChia0()
			return
		}
		val v1=v1.toFloat()
		val v2=v2.toFloat()
		val kq:Float=when(operatorr){
			"cộng","+"-> v1 + v2
			"trừ","-"-> v1 - v2
			"nhân","*"-> v1 * v2
			"chia","/"-> v1 / v2
			else -> -111111111.0f
		}
		if(kq!=-111111111.0f){
			//dung
			mIView.showResultCalculator(kq,"$v1$operatorr$v2")
		}else{
			mIView.showCantCalculator("$v1$operatorr$v2")
		}
	}

	override fun handleKqxs(mien: String?) {
		Log.e("command","handle kqxs")
		//miền bắc|miền nam|miền trung
		stopThinking()
		if(mien=="miền bắc"){
			mModel.loadKetQuaXoSo()
		}else{
			mIView.showNoResultKqxsFor(mien=mien)
		}
	}

	override fun handleFlash(mode: String?) {
		Log.e("command","handle flash")
		//bật|tắt
		stopThinking()
		if(mode=="bật"){
			mModel.turnOnFlash()
		}else{
			mModel.turnOffFlash()
		}
	}

	override fun handleWeather(locate: String?) {
		Log.e("command","handle weather")
		//ten dia diem vd: thai nguyen
		stopThinking()
		if(locate!=null) {
			mModel.loadThoiTiet(locate = locate!!)
		}else{
			//neu locate==null thi se lay thoi tiet mac dinh tai vi tri cua minh

			//load lat,lon
//			val gpsTracker: GPSTracker = GPSTracker(mContext)
//
//			val lat:Double
//			val lon:Double
//			if (gpsTracker.isGPSTrackingEnabled)
//			{
//				lat = gpsTracker.latitude
//
//				lon = gpsTracker.longitude
//			}
//			else
//			{
//				// can't get location
//				// GPS or Network is not enabled
//				// Ask user to enable GPS/network in settings
//				gpsTracker.showSettingsAlert()
//				return
//			}
//			mModel.loadThoiTiet(lat=lat.toFloat(),lon=lon.toFloat())
//			Log.e("lat",lat.toString())
//			Log.e("lon",lon.toString())
		}
	}

	override fun handleBrightNess(value: String?) {
		Log.e("command","handle bright ness")
		//tăng|giảm
		stopThinking()
		mModel.setBrightNess(mode=value)
	}

	fun handleRequest(data: String) {
		mModel.handleRequest(data.toLowerCase())
	}

	override fun handleSearchWeb(key: String?) {
		stopThinking()
		val url="https://www.google.com/search?q=$key"
		mModel.onOpenBrowser(url=url)
	}

	override fun koHieu(data: String) {
		stopThinking()
		mIView.showKoHieu(data)
	}

	override fun showReplyResultTranslate(result: String) {
		mIView.showResultTranslate(result)
	}



	override fun updateWindowAfterSetBrightNess(brightness: Int) {
		mIView.updateWindowAfterSetBrightNess(brightness)
		mIView.showReplyAfterSetBrightNess(brightness=brightness)
	}

	override fun showReplyFlashOff() {
		mIView.showReplyFlashOff()
	}

	override fun showReplyFlashOn() {
		mIView.showReplyFlashOn()
	}

	fun onClickItemContact(itemContact: ItemContact) {
		val it = Intent(Intent.ACTION_VIEW)
		//it.data = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, itemContact.id)
		val uri = ContactsContract.Contacts.getLookupUri(itemContact.id.toLong(), itemContact.lookupkey)
		it.data=uri
		Log.e("id contact",itemContact.id)
		try {
			mContext.startActivity(it)
		}catch (e:Exception){
			Log.e("loi",e.toString())
		}
	}
}