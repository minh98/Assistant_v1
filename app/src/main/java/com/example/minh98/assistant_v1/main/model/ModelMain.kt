package com.example.minh98.assistant_v1.main.model

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.hardware.Camera
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.AsyncTask
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.Window
import com.example.minh98.assistant_v1.listContact.ItemContact
import com.example.minh98.assistant_v1.main.presenter.IPresenterMain
import com.example.minh98.assistant_v1.model.HandleString
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ItemThoiTiet
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ItemThoiTiet7Ngay
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ThoiTiet7Ngay
import com.example.minh98.assistant_v1.viewsLayout.TiGiaNgoaiTe.ItemTiGia
import com.example.minh98.assistant_v1.viewsLayout.TiGiaNgoaiTe.ItemTiGiaNgoaiTe
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


/**
 * Created by minh98 on 09/09/2017.
 */
class ModelMain(private val mContext: Context, val mIPresenter: IPresenterMain) {

	var speech: SpeechRecognizer
	var intentSpeech: Intent
	var isListening = false
	var camera: Camera? = null
	var param: Camera.Parameters? = null
	var hasFlash = false
	private lateinit var dataNumberTaxi: MutableList<MutableList<String>>
	private lateinit var dataUrlMap: MutableList<MutableList<String>>


	init {
		speech = SpeechRecognizer.createSpeechRecognizer(mContext)
		//init intent
		intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
		initIntent()
		//set listener for speech
		setListenerSpeech()
//		handle=handlers()
		readNumberTaxi()
		readUrlMapp()
	}

	private fun setListenerSpeech() {
		speech.setRecognitionListener(listener())
	}

	private fun initIntent() {
		/**
		 * by vav
		 */
		intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
		intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("vi"))
		intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale("vi"))
		intentSpeech.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, Locale("vi"))
		intentSpeech.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
		intentSpeech.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
		intentSpeech.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, mContext.packageName)


		/**
		 * by red hat :))
		 */

		//		intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
//				Locale("vi"))
//		intentSpeech.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
//				mContext.packageName)
//		intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//
//		intentSpeech.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
//
//		intentSpeech.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true)
//
//		intentSpeech.putExtra("android.speech.extra.DICTATION_MODE", true)
//
//		intentSpeech.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, true)

		//xong intent
	}

	fun startListen() {
		speech.startListening(intentSpeech)
		mIPresenter.startAnimationListening()
		log("listenning", "started")
	}

	fun stopListen() {
		speech.stopListening()
	}

	private fun log(k: String, v: String) {
		Log.e(k, v)
	}

	private fun getErrorText(errorCode: Int): String {
		val message: String = when (errorCode) {
			SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
			SpeechRecognizer.ERROR_CLIENT -> "Client side error"
			SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
			SpeechRecognizer.ERROR_NETWORK -> "Network error"
			SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
			SpeechRecognizer.ERROR_NO_MATCH -> "No match"
			SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
			SpeechRecognizer.ERROR_SERVER -> "error from server"
			SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
			else -> "Didn't understand, please try again."
		}
		return message
	}

	fun speechDestroy() {
		speech.destroy()
	}

	fun speechInit() {
		speech = SpeechRecognizer.createSpeechRecognizer(mContext)
		speech.setRecognitionListener(listener())
	}

	inner class listener : RecognitionListener {
		override fun onReadyForSpeech(p0: Bundle?) {
			log("listening", "onReadyForSpeech")
		}

		override fun onRmsChanged(p0: Float) {
			log("listening", "on rms changed")
		}

		override fun onBufferReceived(p0: ByteArray?) {
			log("listening", "on bufferreceived")
		}

		override fun onPartialResults(p0: Bundle?) {
			log("listening", "on partialresults")
			//xu li data
			val data = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)
			mIPresenter.onPartialResults(data)
		}

		override fun onEvent(p0: Int, p1: Bundle?) {
			log("listening", "on event")
		}

		override fun onBeginningOfSpeech() {
			log("listening", "on beginningofspeech")
		}

		override fun onEndOfSpeech() {
			log("listening", "on end of speech")
		}

		override fun onError(p0: Int) {
			log("listening", getErrorText(p0))
			speech.cancel()
			mIPresenter.onError()
			isListening = false
		}

		override fun onResults(p0: Bundle?) {
			log("listening", "on results")
			//xu ly ket qua
			val data = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)
			mIPresenter.onResults(data)
			isListening = false

		}
	}

	fun loadTiGiaNgoaiTe() {
		AsynTiGiaNgoaiTe().execute("http://dongabank.com.vn/exchange/export")//
	}

	fun loadThoiTiet(locate: String? = null, lat: Float? = null, lon: Float? = null) {
		val locate = Uri.encode(locate)


		val urllatloncurrent = "http://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&lang=vi&units=metric&appid=53fbf527d52d4d773e828243b90c1f8e"

		val urllatlon7ngay = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=$lat&lon=$lon&lang=vi&units=metric&appid=53fbf527d52d4d773e828243b90c1f8e"

		val urllocatecurrent = "http://api.openweathermap.org/data/2.5/weather?q=$locate,vietnam&lang=vi&units=metric&appid=53fbf527d52d4d773e828243b90c1f8e"

		val urllocate7ngay = "http://api.openweathermap.org/data/2.5/forecast/daily?q=$locate,vietnam&lang=vi&units=metric&appid=53fbf527d52d4d773e828243b90c1f8e"


		if (locate != null) {
			AsynThoiTietCurrent().execute(urllocatecurrent)
			AsynThoiTiet7Ngay().execute(urllocate7ngay)
		} else {
			AsynThoiTietCurrent().execute(urllatloncurrent)
			AsynThoiTiet7Ngay().execute(urllatlon7ngay)
		}

	}

	fun loadKetQuaXoSo() {
		AsynKetQuaXoSo().execute("http://xskt.com.vn/rss-feed/mien-bac-xsmb.rss")
	}

	//load ti gia ngoai te
	inner class AsynTiGiaNgoaiTe : AsyncTask<String, Unit, ItemTiGia>() {

		override fun doInBackground(vararg p0: String?): ItemTiGia {

			val url = URL(p0[0])

			val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ")
			connection.setRequestProperty("Accept", "*/*")
			val isr: InputStreamReader = InputStreamReader(connection.inputStream, "UTF-8")
			val br: BufferedReader = BufferedReader(isr)

			var s = br.readText()

			br.close()
			isr.close()
			s = s.replace("(", "")
			s = s.replace(")", "")
			val gs = Gson()
			val dstg: ItemTiGia = gs.fromJson<ItemTiGia>(s, ItemTiGia::class.java)
			log("size:", dstg.items.size.toString())
			dstg.items = dstg.items.filter {
				it.type.equals("AUD")
						|| it.type == "EUR"
						|| it.type == "AUD"
						|| it.type == "GBP"
						|| it.type == "JPY"
						|| it.type == "USD"
			} as MutableList<ItemTiGiaNgoaiTe>
			return dstg
		}

		override fun onPostExecute(result: ItemTiGia) {
			mIPresenter.showTiGiaNgoaiTe(result)
		}

	}

	//load thoi tiet current
	inner class AsynThoiTietCurrent : AsyncTask<String, Unit, ItemThoiTiet>() {

		override fun doInBackground(vararg p0: String?): ItemThoiTiet {

			val url = URL(p0[0])

			val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//			connection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ")
//			connection.setRequestProperty("Accept", "*/*")
			val isr: InputStreamReader = InputStreamReader(connection.inputStream, "UTF-8")
			val br: BufferedReader = BufferedReader(isr)

			val s = br.readText()

			br.close()
			isr.close()
			val gs = Gson()
			val tt: ItemThoiTiet = gs.fromJson<ItemThoiTiet>(s, ItemThoiTiet::class.java)
			log("size:", tt.weather.size.toString())
			return tt
		}

		override fun onPostExecute(result: ItemThoiTiet) {
			mIPresenter.sendThoiTietCurrent(result)
		}

	}

	//load thoi tiet 7ngay
	inner class AsynThoiTiet7Ngay : AsyncTask<String, Unit, ItemThoiTiet7Ngay>() {

		override fun doInBackground(vararg p0: String?): ItemThoiTiet7Ngay {
			fun round(a: Float) = Math.round(a)

			val url = URL(p0[0])

			val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//			connection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ")
//			connection.setRequestProperty("Accept", "*/*")
			val isr: InputStreamReader = InputStreamReader(connection.inputStream, "UTF-8")
			val br: BufferedReader = BufferedReader(isr)

			val s = br.readText()

			br.close()
			isr.close()
//			val gs = Gson()
//			val tt: ItemThoiTiet = gs.fromJson<ItemThoiTiet>(s, ItemThoiTiet::class.java)
			//log("size:", tt.weather.size.toString())
			val jsonObj: JsonObject = JsonParser().parse(s).asJsonObject
			val jsonArr: JsonArray = jsonObj.getAsJsonArray("list")
			val items = ItemThoiTiet7Ngay()
			for (i in 0..6) {
				val jsonobj: JsonObject = jsonArr[i].asJsonObject
				val item = ThoiTiet7Ngay(
						jsonobj.get("dt").asLong,
						jsonobj.get("weather").asJsonArray.get(0).asJsonObject.get("icon").asString,
						round(jsonobj.get("temp").asJsonObject.get("max").asFloat),
						round(jsonobj.get("temp").asJsonObject.get("min").asFloat))
				items.items.add(item)
			}

			return items
		}

		override fun onPostExecute(result: ItemThoiTiet7Ngay) {
			mIPresenter.sendThoiTiet7Ngay(result)
		}
	}


	//load kqxs
	inner class AsynKetQuaXoSo : AsyncTask<String, Unit, MutableList<String>>() {

		override fun doInBackground(vararg p0: String?): MutableList<String> {

			val url = URL(p0[0])

			val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//			connection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ")
//			connection.setRequestProperty("Accept", "*/*")
			val isr: InputStreamReader = InputStreamReader(connection.inputStream, "UTF-8")
			val br: BufferedReader = BufferedReader(isr)

			val s = br.readText()


			br.close()
			isr.close()

			var data = mutableListOf<String>()
			val kqxs = findKqxs(s)
			Log.e("s", kqxs)

			val title = findTitle(kqxs)
			Log.e("title", title)

			val value = findValue(kqxs)
			Log.e("value", value)
			data = value.split("\n") as MutableList<String>
			data = data.map { it.split(":")[1].trim() } as MutableList<String>
			data.add(title)
			Log.e("size", data.size.toString())
			for (i in 0..data.size - 1) {
				Log.e(i.toString(), data[i])
			}
			return data
		}

		private fun findKqxs(s: String): String {
			val open = s.indexOf("<item>")
			val close = s.indexOf("</item>")
			return s.substring(open + 6, close)
		}

		private fun findTitle(s: String): String {
			val open = s.indexOf("<title>")
			val close = s.indexOf("</title>")
			return s.substring(open + 7, close)
		}

		private fun findValue(s: String): String {
			val open = s.indexOf("<description>")
			val close = s.indexOf("</description>")
			return s.substring(open + 14, close)
		}

		override fun onPostExecute(result: MutableList<String>) {
			mIPresenter.showKqxs(result)
		}

	}


	//TODO command
	fun onOpenMap(locate: String? = null, from: String? = null, to: String? = null) {
		/* che do streetView tai vi tri nhat dinh
		// Create a Uri from an intent string. Use the result to create an Intent.
		val gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988")

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
		val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
// Make the Intent explicit by setting the Google Maps package
		mapIntent.`package` = "com.google.android.apps.maps"

// Attempt to start an activity that can handle the Intent
		startActivity(mapIntent) */


		/**
		 * che do xem ban do tai vi tri nhat dinh

		val gmmIntentUri = Uri.parse("geo:37.7749,-122.4194")
		val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
		mapIntent.`package` = "com.google.android.apps.maps"
		if (mapIntent.resolveActivity(packageManager) != null) {
		startActivity(mapIntent)
		}*/


		/**
		 * // Search for restaurants nearby


		val gmmIntentUri = Uri.parse("geo:0,0?q=restaurants")
		val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
		mapIntent.`package` = "com.google.android.apps.maps"
		startActivity(mapIntent)*/


		/**
		 * Search for restaurants in San Francisco

		//
		val gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?q=restaurants")
		val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
		mapIntent.`package` = "com.google.android.apps.maps"
		startActivity(mapIntent) */


		/**
		 * zoom 10

		val gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?z=10&q=restaurants")
		val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
		mapIntent.`package` = "com.google.android.apps.maps"
		startActivity(mapIntent) */


		/**
		 * location search

		val gmmIntentUri = Uri.parse("geo:0,0?q=cntt thai nguyen, vietnam")
		val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
		mapIntent.`package` = "com.google.android.apps.maps"
		startActivity(mapIntent)*/


		/**
		 * tim duong di giua 2 diem(location hien tai va diem dich)


		val gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia&mode=b")
		val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
		mapIntent.`package` = "com.google.android.apps.maps"
		startActivity(mapIntent)*/

		/**
		 * tim duong di giua 2 diem
		 */
		if (from != null && to != null) {

			val saddr = "$from,viet nam"
			val daddr = "$to,viet nam"
			val uri = "http://maps.google.com/maps?saddr=$saddr&daddr=$daddr"
			val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
			intent.`package` = "com.google.android.apps.maps"
			try {
				startActivity(intent)
			} catch (e: Exception) {
				startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
			}
		}
		if (locate != null) {
			val gmmIntentUri = Uri.parse("geo:0,0?q=$locate")
			val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
			mapIntent.`package` = "com.google.android.apps.maps"
			try {
				startActivity(mapIntent)
			} catch (e: Exception) {
				startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com/maps/search/$locate")))
			}
		}
		mIPresenter.showReplyOpenedMap()
	}

	fun onOpenYoutube(search: String? = null) {
		val searchRequest = search?.replace(" ", "+")
		val itWeb: Intent
		val itApp: Intent

		if (searchRequest != null) {

			itWeb = Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.youtube.com/results?search_query=$searchRequest"))

			itApp = Intent(Intent.ACTION_VIEW)
			itApp.`package` = "com.google.android.youtube"
			itApp.data = Uri.parse("https://www.youtube.com/results?search_query=$searchRequest")
		} else {
			itWeb = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"))

			itApp = Intent(Intent.ACTION_VIEW)
			itApp.`package` = "com.google.android.youtube"

		}

		//test
		val intent = Intent(Intent.ACTION_VIEW)
		intent.`package` = "com.google.android.youtube"
		intent.data = Uri.parse("https://www.youtube.com/results?search_query=li+tieu+long")

		try {
			startActivity(itApp)
		} catch (e: Exception) {
			startActivity(itWeb)
		}
		mIPresenter.showReplyOpenedYoutube()
	}

	fun onCall(tel: String) {
		val it = Intent(Intent.ACTION_CALL)
		it.data = Uri.fromParts("tel", tel, null)
		try {
			startActivity(it)
			mIPresenter.showReplyCalled(number = tel)
		} catch (e: Exception) {
			Log.e("call", "device is not support calling")
		}
	}

	private fun getCamera() {
		if (camera == null) {
			try {
				camera = Camera.open()
				param = camera?.parameters
			} catch (e: RuntimeException) {
				Log.e("Camera Error.", e.message)
			}

		}
	}

	fun turnOffFlash() {
		if (camera == null || param == null) {
			return
		}
		param = camera?.parameters
		param?.flashMode = Camera.Parameters.FLASH_MODE_OFF
		camera?.parameters = param
		camera?.stopPreview()
		mIPresenter.showReplyFlashOff()
	}

	fun turnOnFlash() {
		if (camera == null || param == null) {
			return
		}
		param = camera?.parameters
		param?.flashMode = Camera.Parameters.FLASH_MODE_TORCH
		camera?.parameters = param
		camera?.startPreview()
		mIPresenter.showReplyFlashOn()
	}

	fun onOpenBrowser(url: String? = null) {
		val it = Intent(Intent.ACTION_VIEW)
		it.data = Uri.parse(url)
		startActivity(it)
		mIPresenter.showReplyOpenedUrl()
	}

	fun onOpenSms(address: String = "", smsBody: String = "") {
		val it = Intent(Intent.ACTION_VIEW)
		it.type = "vnd.android-dir/mms-sms"
		it.putExtra("address", address)
		it.putExtra("sms_body", smsBody)
		startActivity(it)
		mIPresenter.showReplyOpenedSms()
	}

	fun onSetAlarm(hour: Int, min: Int) {
		val it = Intent(AlarmClock.ACTION_SET_ALARM)
				.putExtra(AlarmClock.EXTRA_HOUR, hour)
				.putExtra(AlarmClock.EXTRA_MINUTES, min)
				//.putExtra(AlarmClock.EXTRA_MESSAGE,"day la message cua alarm")
				.putExtra(AlarmClock.EXTRA_SKIP_UI, true)
		startActivity(it)
		mIPresenter.showReplySetedAlarm(hour = hour, min = min)
	}

	private fun startActivity(intent: Intent) {
		mContext.startActivity(intent)
	}


	fun onStop() {
		if (camera != null) {
			camera?.release()
			camera = null
		}
	}

	fun onStart() {
		getCamera()
	}

	//TODO xu ly request
	/**
	 * xu li du lieu nguoi dung noi vao thanh lenh cu the
	 */
	fun handleRequest(data: String) {
		val data = data.toLowerCase()
		if (handle1(data)) {
			//la gi(tim kiem, translate)
		} else if (handle2(data)) {
			//bang bao nhieu(tinh toan)
		} else if (handle3(data)) {
			//o dau (tim vi tri)
		} else if (handle4(data)) {
			//ket qua xo so( ket qua so xo)
		} else if (handle5(data)) {
			//den flash(bat/tat den flash)
		} else if (handle6(data)) {
			//video (xem video, mo video, tim video)
		} else if (handle7(data)) {
			//thoi tiet( thoi tiet o ha nam, thoi tiet o viet nam)
		} else if (handle8(data)) {
			//am lich(ngay 1 thang 2 la ngay bao nhieu am lich)
		} else if (handle9(data)) {
			//taxi(goi taxi mai linh thai nguyen..)
		} else if (handle10(data)) {
			//ngoai te(ti gia ngoai te..)
		} else if (handle11(data)) {
			//do sang man hinh(tang so sang man hinh len mot chut)
		} else if (handle12(data)) {
			//tai khoan(kiem tra tai khoan)
		} else if (handle13(data)) {
			//tin nhan(gui tin nhan,gui ti nhan cho minh 5 gio di chay bo ho)
		} else if (handle14(data)) {
			//tim so(tim so dien thoai cua minh)
		} else if (handle15(data)) {
			//vao trang(vao trang xem.vn)
		} else if (handle16(data)) {
			//gan day(co nha hang nao gan day khong)
		} else if (handle17(data)) {
			//wifi(bat/tat wifi)
		} else if (handle18(data)) {
			//bao thuc(dat bao thuc lluc 6 gio 10
		} else if (handle19(data)) {
			//tim duong (tim duong tu ha nam den thai nguyen)
		} else {
			mIPresenter.koHieu(data)
		}
	}

	private fun handle1(data: String): Boolean {
		//la gi(tim kiem, translate)
		//la gi hoac nghia (tieng anh) la gi


		/**
		 * key: la gi,nghia la gi
		 * --nghia (tieng anh) la gi: phan tich xem nguoi dung muon dich cau do sang ngon ngu nuoc nao,
		 * neu khong tim ra duoc ngon ngu cua nuoc nao thi se searchWeb nhu binh thuong
		 * --la gi: searchWeb luon khong can phan tich cau
		 */
		//-nghia la gi

		val reg4 = Regex("""^(nghĩa trong|nghĩa) tiếng (anh|hàn quốc|pháp|nhật) của (.*?) (là gì vậy|là gì|là)$""")

		val reg2 = Regex("""^(nghĩa của câu|nghĩa của từ|nghĩa của) (.*?) trong tiếng (anh|hàn quốc|pháp|nhật) (là gì|là)$""")

		val reg = Regex("""^(.*?) (nghĩa tiếng|trong tiếng|tiếng) (anh|nhật|hàn quốc|pháp) (nghĩa là gì|là gì)( nhỉ| vậy| vậy nhỉ| thế nhỉ| thế)?$""")


		val reg3 = Regex("""^(.*?) (nghĩa là gì|là gì)( nhỉ| vậy| vậy nhỉ| thế nhỉ| thế)?$""")



		if (reg4.matches(data)) {
			//khop regex 2
			val entire = reg4.matchEntire(data)?.destructured!!
			val keywordSearch = entire.component3()
			val ngonNgu = entire.component2()
			mIPresenter.handleTranslate(keywordSearch, ngonNgu)
			return true
		} else if (reg2.matches(data)) {
			//khop regex 2
			val entire = reg2.matchEntire(data)?.destructured!!
			val keywordSearch = entire.component2()
			val ngonNgu = entire.component3()
			mIPresenter.handleTranslate(keywordSearch, ngonNgu)
			return true
		} else if (reg.matches(data)) {
			//truong hop nay nghia la nguoi dung dang muon translate
			val entire = reg.matchEntire(data)?.destructured!!
			val keywordSearch = entire.component1()
			val ngonNgu = entire.component3()
			mIPresenter.handleTranslate(keywordSearch, ngonNgu)
			return true
		} else {
			//neu khong khop regex
			//kiem tra regex 2
			if (reg3.matches(data)) {
				//nguoi dung muon tim kiem noi dung
				mIPresenter.handleSearchWeb(key = reg3.matchEntire(data)?.destructured?.component1())
				return true
			} else {
				//khong khop bat cu regex nao=> searchWeb
				Log.e("log4", "khong hop le")
				return false
			}
		}
	}

	private fun handle2(data: String): Boolean {
		//bang bao nhieu(tinh toan)
		val reg = Regex("""^(\d+) (cộng|trừ|nhân|chia|\+|\-|\*|\/) (\d+) (bằng bao nhiêu|bằng mấy|bằng|=)?$""")
		if (reg.matches(data)) {
			val entire = reg.matchEntire(data)?.destructured!!
			val v1 = entire.component1()
			val operatorr = entire.component2()
			val v2 = entire.component3()
			mIPresenter.handleCalculator(v1, operatorr, v2)
			return true
		} else {
			return false
		}
	}

	private fun handle3(data: String): Boolean {
		//o dau (tim vi tri)
		val reg1 = Regex("""^(hãy )?(tìm kiếm |tìm )?vị trí( của)? (.*)$""")
		val reg2 = Regex("""^(.*) (ở đâu|ở chỗ nào)( thế nhỉ| vậy nhỉ| nhỉ| vậy| thế)?$""")
		if (reg1.matches(data) && !reg2.matches(data)) {
			mIPresenter.handleMap(locate = reg1.matchEntire(data)?.destructured?.component4())
			return true
		} else if (reg2.matches(data)) {
			mIPresenter.handleMap(locate = reg2.matchEntire(data)?.destructured?.component1())
			return true
		} else {
			return false
		}

	}

	private fun handle4(data: String): Boolean {
		//ket qua xo so( ket qua so xo)
		val reg1 = Regex("""^(hãy )?(xem |mở |kiểm tra |bật )?kết quả (sổ số|xổ xố|sổ xố|xổ số) (miền bắc|miền nam|miền trung)( đi)?$""")
		if (reg1.matches(data)) {
			mIPresenter.handleKqxs(mien = reg1.matchEntire(data)?.destructured?.component4())
			return true
		} else {
			return false
		}
	}

	private fun handle5(data: String): Boolean {
		//den flash(bat/tat den flash)
		val reg1 = Regex("""^(hãy )?(bật|tắt)( đèn)? flash( lên đi| đi)?$""")
		if (reg1.matches(data)) {
			mIPresenter.handleFlash(mode = reg1.matchEntire(data)?.destructured?.component2())
			return true
		} else {
			return false
		}

	}

	private fun handle6(data: String): Boolean {
		//video (xem video, mo video, tim video)
		val reg1 = Regex("""^(hãy )?(xem|mở|bật) video( đi)?$""")
		val reg2 = Regex("""^(hãy )?(xem|mở|bật) video( của)? (.*?)( giúp tôi đi| giúp tôi| đi)?$""")
		if (reg1.matches(data) && !reg2.matches(data)) {
			mIPresenter.handleYoutube()
			return true
		} else if (reg2.matches(data)) {
			mIPresenter.handleYoutube(search = reg2.matchEntire(data)?.destructured?.component4())
			return true
		} else {
			return false
		}
	}

	private fun handle7(data: String): Boolean {
		//thoi tiet( thoi tiet o ha nam, thoi tiet o viet nam)

		//
		val reg1 = Regex("""^(hãy |mở )?(xem dự báo thời tiết|dự báo thời tiết|thời tiết)( của| ở| tại)? (.*?)( giúp tôi đi| giúp tôi| đi)?$""")
		val reg2 = Regex("""^(bật |mở |xem )?(dự báo thời tiết|thời tiết)( giúp tôi đi| giúp tôi| đi)?$""")

		if (reg1.matches(data)) {
			mIPresenter.handleWeather(locate = reg1.matchEntire(data)?.destructured?.component4())
			return true
		} else if (reg2.matches(data)) {
			Log.e("log2", reg2.matchEntire(data)?.destructured?.component2())
			mIPresenter.handleWeather()
			return true
		} else {
			Log.e("log3", "khong hop le")
			return false
		}
	}

	private fun handle8(data: String): Boolean {
		//am lich(ngay 1 thang 2 la ngay bao nhieu am lich)
		//chua co nguon du lieu
		//TODO
		return false
	}

	private fun handle9(data: String): Boolean {
		//taxi(goi taxi mai linh thai nguyen..)
		//TODO
		var tinhThanh = ""
		for (i in 0 until dataNumberTaxi.size) {
			if (i < dataNumberTaxi.size - 1)
				tinhThanh += "${dataNumberTaxi[i][0]}|"
			else
				tinhThanh += "${dataNumberTaxi[i][0]}|"
		}

		val reg1 = Regex("^(hãy )?(gọi|gọi cho|gọi đến|gọi tới) taxi ($tinhThanh)( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$")

		if (reg1.matches(data)) {
			mIPresenter.handleCallTaxi(locate = reg1.matchEntire(data)?.destructured?.component3(),
					data = dataNumberTaxi)
			return true
		} else {
			return false
		}
	}

	private fun handle10(data: String): Boolean {
		//ngoai te(ti gia ngoai te..)
		val reg1 = Regex("""^(hãy )?(xem |mở )?tỉ giá ngoại tệ( bây giờ| ngày hôm nay| hôm nay| hiện tại)?( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$""")

		if (reg1.matches(data)) {
			mIPresenter.onShowTiGiaNgoaiTe()
			return true
		} else {
			return false
		}
	}

	private fun handle11(data: String): Boolean {
		//do sang man hinh(tang do sang man hinh len mot chut)
		val reg1 = Regex("""^(hãy )?(tăng|giảm) độ sáng màn hình( lên| xuống)?( một chút| một tí| một tẹo)?( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$""")

		if (reg1.matches(data)) {
			mIPresenter.handleBrightNess(value = reg1.matchEntire(data)?.destructured?.component2())
			return true
		} else {
			return false
		}
	}

	private fun handle12(data: String): Boolean {
		//tai khoan(kiem tra tai khoan)
		val reg1 = Regex("""^(hãy )?(kiểm tra|xem) tài khoản( gốc| phụ| khuyến mãi| khuyến mại| chính)?( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$""")

		if (reg1.matches(data)) {
			mIPresenter.handleCallUSSD(mode = reg1.matchEntire(data)?.destructured?.component3())
			return true
		} else {
			return false
		}
	}

	private fun handle13(data: String): Boolean {
		//tin nhan(gui tin nhan,gui ti nhan cho minh 5 gio di chay bo ho)
		val reg1 = Regex("""^(hãy mở| hãy bật| mở| bật)?(gửi tin nhắn|tin nhắn|nhắn tin)( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$""")

		val reg2 = Regex("""^(hãy mở| hãy bật| mở| bật)?(gửi tin nhắn|tin nhắn|nhắn tin) (cho|đến|tới|vào)(\s\S+\s)(.*?)( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$""")

		if (reg1.matches(data) && !reg2.matches(data)) {
			mIPresenter.handleSMS()
			return true
		} else if (reg2.matches(data)) {
			mIPresenter.handleSMS(address = reg2.matchEntire(data)?.destructured?.component4()!!, smsBody = reg2.matchEntire(data)?.destructured?.component5()!!)
			return true
		} else {
			return false
		}
	}

	private fun handle14(data: String): Boolean {
		//tim so(tim so dien thoai cua minh)
		val reg1 = Regex("""^(hãy )?tìm số( điện thoại)? của(\s\S+)( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$""")

		if (reg1.matches(data)) {
			mIPresenter.handleFindNumberPhone(name = reg1.matchEntire(data)?.destructured?.component3())
			return true
		} else {
			return false
		}
	}

	private fun handle15(data: String): Boolean {
		//vao trang(vao trang xem.vn)
		val reg1 = Regex("""^(hãy )?(vào|truy cập|mở)( trang| web)? (\S.*?)( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$""")

		if (reg1.matches(data)) {
			mIPresenter.handleOpenBrowser(url = reg1.matchEntire(data)?.destructured?.component4())
			return true
		} else {
			return false
		}
	}

	private fun handle16(data: String): Boolean {
		//gan day(co nha hang nao gan day khong)
		val reg1 = Regex("""^(có |tìm kiếm |tìm |xem)?(.*?) (nào )?(gần đây|cạnh đây|xung quanh đây)( hay không| không nhỉ| không vậy| không)?$""")

		if (reg1.matches(data)) {
			mIPresenter.handleSearchMapGanDay(locate = reg1.matchEntire(data)?.destructured?.component2())
			return true
		} else {
			return false
		}
	}

	private fun handle17(data: String): Boolean {
		//wifi(bat/tat wifi)
		val reg1 = Regex("""^(hãy )?(bật|tắt) wifi( giúp tôi với| giúp tôi đi| giúp tôi| lên đi| đi)?$""")

		if (reg1.matches(data)) {
			mIPresenter.handleWifi(mode = reg1.matchEntire(data)?.destructured?.component2())
			return true
		} else {
			return false
		}
	}

	private fun handle18(data: String): Boolean {
		//bao thuc(dat bao thuc lluc 6 gio 10
		val reg1 = Regex("""^(hãy )?(đặt báo thức|báo thức tôi|báo thức|đánh thức tôi) (\D+)(\d+)(\D+)( \d+)?( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$""")

		val reg2 = Regex("""^(hãy )?(đặt báo thức|báo thức tôi|báo thức|đánh thức tôi) (\D+) (\d+):(\d+)( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$""")
		if (reg2.matches(data)) {
			mIPresenter.handleAlarm(hour = reg2.matchEntire(data)?.destructured?.component4(),
//					mode = reg2.matchEntire(data)?.destructured?.component5(),
					min = reg2.matchEntire(data)?.destructured?.component5())
			return true
		} else if (reg1.matches(data)) {
			mIPresenter.handleAlarm(hour = reg1.matchEntire(data)?.destructured?.component4(),
					mode = reg1.matchEntire(data)?.destructured?.component5(),
					min = reg1.matchEntire(data)?.destructured?.component6())
			return true
		} else {
			return false
		}
	}

	private fun handle19(data: String): Boolean {
		//tim duong (tim duong tu ha nam den thai nguyen)
		val reg1 = Regex("""^(hãy )?(tìm kiếm |tìm )?(đường)( đi| đến)? (bắt đầu từ|từ) (.*) (đến|tới|đi|về) (.*)( giúp tôi với| giúp tôi đi| giúp tôi| đi)?$""")

		if (reg1.matches(data)) {
			mIPresenter.handleFindDirection(from = reg1.matchEntire(data)?.destructured?.component6(),
					to = reg1.matchEntire(data)?.destructured?.component8())
			return true
		} else {
			return false
		}
	}

	private fun handleB2(i: Int, key: String, data: String) {
		//set lenh dua theo i
		when (i) {
			0 -> {
				//la gi hoac nghia (tieng anh) la gi


				/**
				 * key: la gi,nghia la gi
				 * --nghia (tieng anh) la gi: phan tich xem nguoi dung muon dich cau do sang ngon ngu nuoc nao,
				 * neu khong tim ra duoc ngon ngu cua nuoc nao thi se searchWeb nhu binh thuong
				 * --la gi: searchWeb luon khong can phan tich cau
				 */
				//-nghia la gi
				val reg = Regex("""^(.*) (nghia tieng|trong tieng|tieng) (anh|nhat|han|trung quoc|thai lan) (la gi|nghia la gi)$""")

				val reg2 = Regex("""^(nghia cua tu|nghia cua) (.*?) trong tieng (anh|nhat|han|trung quoc|thai lan) la gi$""")

				val reg3 = Regex("""^(.*) (la gi|ngia la gi)$""")
				if (reg.matches(data) && !reg2.matches(data)) {
					//truong hop nay nghia la nguoi dung dang muon translate
					val entire = reg.matchEntire(data)?.destructured!!
					val keywordSearch = entire.component1()
					val ngonNgu = entire.component3()
					//handleTranslate(keywordSearch,ngonNgu)
				} else {
					//neu khong khop regex
					//kiem tra regex 2
					if (reg2.matches(data)) {
						//khop regex 2
						val entire = reg2.matchEntire(data)?.destructured!!
						val keywordSearch = entire.component2()
						val ngonNgu = entire.component3()
						//handleTranslate(keywordSearch,ngonNgu)
					} else {
						//khong khop bat cu regex nao=> searchWeb
						//onSearchWeb(reg3.matchEntire(data)?.destructured!!.component1())
					}
				}


			}
		}
	}

	private fun readNumberTaxi() {
		dataNumberTaxi = mutableListOf()
		val reader: BufferedReader
		try {
			reader = BufferedReader(
					InputStreamReader(mContext.assets.open("taxi_number"), "UTF-8"))

			// do reading, usually loop until end of file reading
			var fulldata = reader.readText().split("\n")
			for (i in 0 until fulldata.size) {
				//process line
				dataNumberTaxi.add(mutableListOf(fulldata[i].split(":")[0], fulldata[i].split(":")[1]))

			}
			reader.close()
		} catch (e: IOException) {
			//log the exception
			Log.e("ioexception", e.toString())
		}
	}

	private fun readUrlMapp() {
		dataUrlMap = mutableListOf()
		val reader: BufferedReader
		try {
			reader = BufferedReader(
					InputStreamReader(mContext.assets.open("urlmap"), "UTF-8"))

			// do reading, usually loop until end of file reading
			var fulldata = reader.readText().split("\n")
			for (i in 0 until fulldata.size) {
				//process line
				dataUrlMap.add(mutableListOf(fulldata[i].split("|")[0], fulldata[i].split("|")[1]))
			}
			reader.close()
		} catch (e: IOException) {
			//log the exception
			Log.e("ioexception", e.toString())
		}
	}

	fun getNumberPhone(name: String): MutableList<ItemContact> {
		val name=name.trim()
		Log.e("tim sdt",name)
		val phones = mContext.contentResolver
				.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
		val numbers = mutableListOf<ItemContact>()
		while (phones.moveToNext()) {
			val mname = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
			val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
			val id=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))
			val key=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY))
			if (HandleString.convertToKoDau(mname).toLowerCase().contains(HandleString.convertToKoDau(name).toLowerCase())) {
				numbers.add(ItemContact(mname,phoneNumber,id,key))
			}
		}
		phones.close()
		return numbers
	}

	fun getUrlMap() = dataUrlMap

	fun loadTranslate(from: String, lagtarget: String, keywordSearch: String) {
		val url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=$from&tl=$lagtarget&dt=t&q=${Uri.encode(keywordSearch)}"
		AsynTranslate().execute(url)
	}

	//load file translate
	inner class AsynTranslate : AsyncTask<String, Unit, String>() {

		override fun doInBackground(vararg p0: String?): String {

			val url = URL(p0[0])

			val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ")
			connection.setRequestProperty("Accept", "*/*")
			val isr: InputStreamReader = InputStreamReader(connection.inputStream, "UTF-8")
			val br: BufferedReader = BufferedReader(isr)

			var s = br.readText()
			log("translate content:", s)
//			s = s.substring(s.indexOf("\""), s.indexOf("\"", s.indexOf("\""), false))
			s = s.replace(Regex("""(.*?)(\")(.*?)(\")(.*)"""), "$3")
			br.close()
			isr.close()

			log("translate content:", s)
			return s
		}

		override fun onPostExecute(result: String) {
			mIPresenter.showReplyResultTranslate(result)
		}

	}

	fun turnOnWifi() {
		try {
			val wifiManager: WifiManager = mContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
			wifiManager.isWifiEnabled = true
			mIPresenter.showReplyWifiOn()
		} catch (e: Exception) {
			log("turnonwifi", e.toString())
		}
	}

	fun turnOffWifi() {

		try {
			val wifiManager: WifiManager = mContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
			wifiManager.isWifiEnabled = false
			mIPresenter.showReplyWifiOff()
		} catch (e: Exception) {
			log("turnonwifi", e.toString())
		}
	}

	fun onCallUSSD(s: String) {
		onCall(s)
		mIPresenter.showReplyCallUSSD(value = s)
	}

	fun setBrightNess(mode: String?) {
		//Variable to store brightness value
		var brightness: Int = 0
		//Content resolver used as a handle to the system's settings
		var cResolver: ContentResolver
		//Window object, that will store a reference to the current window
		var window: Window
		//Get the content resolver
		cResolver = mContext.getContentResolver()

		try {

			//Get the current system brightness
			brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS)
		} catch (e: Settings.SettingNotFoundException) {
			//Throw an error case it couldn't be retrieved
			Log.e("Error", "Cannot access system brightness")
		}

		//Set the system brightness using the brightness variable value
		//tăng|giảm
		if (mode == "tăng") {
			brightness += 20
			if (brightness > 254) {
				brightness = 254
			}
		} else {
			brightness -= 20
			if (brightness < 1) {
				brightness = 1
			}
		}
		Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
		mIPresenter.updateWindowAfterSetBrightNess(brightness)
	}

}