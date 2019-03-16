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

	private val mModel:ModelMain = ModelMain(mContext,this)

	fun onClickItemCommand(item: ItemCommand) {
		//xu li lenh theo position cua item list
		mIView.addTextRequestToLayout(item.name)
		mModel.handleRequest(item.name)
	}

	override fun onResults(data: String?) {
		mIView.addTextRequestToLayout(data)
		/**
		 * add request xong se xu li text va thuc hien cac lenh tuong ung
		 */
		mModel.handleRequest(data!!.toLowerCase())
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

	fun onStop() {
		mModel.onStop()
	}

	fun onPause() {
	}

	fun onStart() {
		mModel.onStart()
	}

	/**
	 * handle commands
	 */

	override fun handleTranslate(keywordSearch: String, ngonNgu: String) {
		Log.e("command","handleTranslate")
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
		mModel.onOpenMap(from=from,to=to)
	}

	override fun showReplyOpenedMap() {
		mIView.showReplyOpenedMap()
	}

	override fun handleAlarm(hour: String?, mode: String?, min: String?) {
		Log.e("command","handle alarm")
		val mmin=if(min?.isNotEmpty()!!) min.trim().toInt() else 0
		val mhour: Int = if(mode=="giờ kém"){
			if(hour?.trim()?.toInt()!!<1) {
				23
			}else{
				hour.trim().toInt() -1
			}
		}else{
			hour?.trim()?.toInt()!!
		}
		mModel.onSetAlarm(mhour, mmin)

	}

	override fun showReplySetedAlarm(hour: Int, min: Int) {
		mIView.showReplySetedAlarm(hour=hour,min=min)
	}

	override fun handleWifi(mode: String?) {

		Log.e("command","handle wifi")
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
		mModel.onOpenMap(locate = locate)
	}

	override fun handleOpenBrowser(url: String?) {
		Log.e("command","handle open browser")
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
		mModel.loadTiGiaNgoaiTe()
	}

	override fun handleCallTaxi(locate: String?, data: MutableList<MutableList<String>>) {
		Log.e("command","handle call taxi")
		val mdata=data.filter { it.contains(locate) }
		val number=(mdata[0][1]as String).replace(" ","")
		mModel.onCall(number)
	}

	override fun showReplyCalled(number: String) {
		mIView.showReplyCalled(number=number)
	}

	override fun handleYoutube(search: String?) {
		Log.e("command","handle youtube")
		mModel.onOpenYoutube(search)
	}

	override fun showReplyOpenedYoutube() {
		mIView.showReplyOpenedYoutube()
	}

	override fun handleMap(locate: String?) {
		Log.e("command","handle map")
		mModel.onOpenMap(locate = locate)
	}

	override fun handleCalculator(v1: String, operatorr: String, v2: String) {
		Log.e("command","handle calculator")
		//cộng|trừ|nhận|chia
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

	override fun handleFlash(mode: String?) {
		Log.e("command","handle flash")
		//bật|tắt
		if(mode=="bật"){
			mModel.turnOnFlash()
		}else{
			mModel.turnOffFlash()
		}
	}

	override fun handleWeather(locate: String?) {
		Log.e("command","handle weather")
		//ten dia diem vd: thai nguyen
		if(locate!=null) {
			mModel.loadThoiTiet(locate = locate)
		}else{
			mModel.loadThoiTiet()
		}
	}

	override fun handleBrightNess(value: String?) {
		Log.e("command","handle bright ness")
		//tăng|giảm
		mModel.setBrightNess(mode=value)
	}

	fun handleRequest(data: String) {
		mIView.addTextRequestToLayout(data)
		mModel.handleRequest(data.toLowerCase())
	}

	override fun handleSearchWeb(key: String?) {
		val url="https://www.google.com/search?q=$key"
		mModel.onOpenBrowser(url=url)
	}

	override fun koHieu(data: String) {
		mIView.showKoHieu(data)
		handleSearchWeb(data)
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