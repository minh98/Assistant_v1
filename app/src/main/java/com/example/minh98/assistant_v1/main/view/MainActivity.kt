package com.example.minh98.assistant_v1.main.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.minh98.assistant_v1.IntroActivity
import com.example.minh98.assistant_v1.R
import com.example.minh98.assistant_v1.listCommand.AdapterCommandList
import com.example.minh98.assistant_v1.listCommand.ItemCommand
import com.example.minh98.assistant_v1.listContact.AdapterContact
import com.example.minh98.assistant_v1.listContact.ItemContact
import com.example.minh98.assistant_v1.main.presenter.PresenterMain
import com.example.minh98.assistant_v1.model.SharedPreference
import com.example.minh98.assistant_v1.viewsLayout.BotButton
import com.example.minh98.assistant_v1.viewsLayout.BotText
import com.example.minh98.assistant_v1.viewsLayout.KetQuaXoSo.LayoutXoSo
import com.example.minh98.assistant_v1.viewsLayout.MyText
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ItemThoiTiet
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.ItemThoiTiet7Ngay
import com.example.minh98.assistant_v1.viewsLayout.ThoiTiet.LayoutThoiTiet
import com.example.minh98.assistant_v1.viewsLayout.TiGiaNgoaiTe.ItemTiGia
import com.example.minh98.assistant_v1.viewsLayout.TiGiaNgoaiTe.LayoutTiGia


class MainActivity : AppCompatActivity(), IViewMain/*, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener */{

	//button
	private lateinit var btnListen: FloatingActionButton
	private lateinit var btnListCommand: FloatingActionButton
	private lateinit var btnSetting: FloatingActionButton
	//class
	private var mPresenter: PresenterMain? = null

	//boolean
	private var isListen = false

	//digital
	private var stateCircleColor = 0
	//indexUp la vi tri cua view(1) dang di len
	private var indexUp = 1

	//anim

	private lateinit var animHideBtnListen: Animation
	private lateinit var animShowBtnListen: Animation

	private lateinit var animHideCircleListen1: Animation
	private lateinit var animHideCircleListen2: Animation
	private lateinit var animHideCircleListen3: Animation
	private lateinit var animShowCircleListen1: Animation
	private lateinit var animShowCircleListen2: Animation
	private lateinit var animShowCircleListen3: Animation

	private lateinit var animCircleUp1: Animation
	private lateinit var animCircleDown1: Animation
	private lateinit var animCircleUp2: Animation
	private lateinit var animCircleDown2: Animation
	private lateinit var animCircleUp3: Animation
	private lateinit var animCircleDown3: Animation

	private lateinit var animShowTextListening: Animation
	private lateinit var animHideTextListening: Animation
	//circle view
	private lateinit var circleListen1: View
	private lateinit var circleListen2: View
	private lateinit var circleListen3: View
	//text
	private lateinit var tvListening: TextView
	//layout
	private lateinit var layoutResult: LinearLayout
	private lateinit var layoutCircleListening: LinearLayout
	private lateinit var scrollView: ScrollView

	//data
	private lateinit var dataCommand: MutableList<ItemCommand>
	private var itemThoiTiet: ItemThoiTiet? = null
	private var itemThoiTiet7Ngay: ItemThoiTiet7Ngay? = null

	/*
	var for location
	 */
//	var lat:Double=0.0
//	var lon:Double=0.0
//
//	var UPDATE_REQUEST = false
//	var locationRequest: LocationRequest?=null
//	var googleApiClient: GoogleApiClient?=null
//	var lastLocation: Location?=null
//	val UPDATE_INTERNAL = 5000
//	val FASTEST_INTERNAL = 3000
//	val DISPLACEMENT = 10
//	val Play_ServiceResolution_request = 1
//
//	private val MY_PERMISSION_REQUEST_CODE = 7171
//	private val PLAY_SERVICES_RESOLUTION_REQUEST = 7172

	/**--
	 * function handle location
	 */
//
//	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//		when (requestCode) {
//			MY_PERMISSION_REQUEST_CODE -> {
//				if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//					if (checkPlayServices()) {
//						buildGoogleApiClient()
//					}
//				}
//			}
//		}
//	}
//
//
//	private fun LocationUpdate() {
//		if (!UPDATE_REQUEST) {
//
//			UPDATE_REQUEST = true
//			startLocationUpdate()
//		} else {
//
//			UPDATE_REQUEST = false
//			stopLocationUpdate()
//		}
//	}
//
//	private fun stopLocationUpdate() {
//		LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
//	}
//
//	private fun startLocationUpdate() {
//		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//			// TODO: Consider calling
//			//    ActivityCompat#requestPermissions
//			// here to request the missing permissions, and then overriding
//			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//			//                                          int[] grantResults)
//			// to handle the case where the user grants the permission. See the documentation
//			// for ActivityCompat#requestPermissions for more details.
//			return
//		}
//		LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
//	}
//
//
//	private fun createLocationRequest() {
//		locationRequest = LocationRequest()
//		Log.e("init", "locationRequest")
//		locationRequest!!.interval = UPDATE_INTERNAL.toLong()
//		locationRequest!!.fastestInterval = FASTEST_INTERNAL.toLong()
//		locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//		locationRequest!!.smallestDisplacement = DISPLACEMENT.toFloat()
//
//	}
//
//	private fun buildGoogleApiClient() {
//		googleApiClient = GoogleApiClient.Builder(this)
//				.addConnectionCallbacks(this)
//				.addOnConnectionFailedListener(this)
//				.addApi(LocationServices.API)
//				.build()
//		Log.e("init", "googleapiclient")
//	}
//
//	private fun checkPlayServices(): Boolean {
//		val resultcode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
//		if (resultcode != ConnectionResult.SUCCESS) {
//			if (GooglePlayServicesUtil.isUserRecoverableError(resultcode)) {
//				GooglePlayServicesUtil.getErrorDialog(resultcode, this, Play_ServiceResolution_request)
//			} else {
//				Toast.makeText(this, "this device is not supported", Toast.LENGTH_LONG).show()
//			}
//			return false
//		}
//		return true
//	}
//
//
//	override fun onConnected(bundle: Bundle?) {
//		getLocation()
//		if (UPDATE_REQUEST) {
//			startLocationUpdate()
//		}
//	}
//
//	override fun onConnectionSuspended(i: Int) {
//		googleApiClient!!.connect()
//	}
//
//	override fun onConnectionFailed(connectionResult: ConnectionResult) {
//		Toast.makeText(this, "on connecttion failed", Toast.LENGTH_SHORT).show()
//	}
//
//	override fun onLocationChanged(location: android.location.Location) {
//		lastLocation = location
//		getLocation()
//	}
//
//
//	private fun getLocation(): Location? {
////		if (ActivityCompat
////				.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
////				!= PackageManager.PERMISSION_GRANTED
////				&& ActivityCompat
////				.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
////				!= PackageManager.PERMISSION_GRANTED) {
////			return null
////		}
////
////		lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
////		if (lastLocation != null) {
////			lat = lastLocation!!.latitude
////			lon = lastLocation!!.longitude
////			Log.e("lat",lat.toString())
////			Log.e("lon",lon.toString())
////			return lastLocation
////		} else {
////			return null
////		}
//		return null
//	}

	/**
	 * end handle location
	 */

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			val w = window // in Activity's onCreate() for instance
			w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

			val param: RelativeLayout.LayoutParams = (findViewById(R.id.scrollView) as ScrollView).layoutParams as RelativeLayout.LayoutParams
			val height = 24 * getResources().getDisplayMetrics().density
			param.topMargin = height.toInt()
			(findViewById(R.id.scrollView) as ScrollView).layoutParams = param
		}


		checkFirstOpen()
		initView()

		//test :))
		layoutResult.addView(BotText(this).setText("xin chao! toi co the giup gi cho ban"))
		initListener()

		/**
		 * for location
		 */
//		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//			//run time request permission
//			ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), MY_PERMISSION_REQUEST_CODE)
//		} else {
//			if (checkPlayServices()) {
//				buildGoogleApiClient()
//				createLocationRequest()
//			}
//		}
	}

	private fun initView() {
		//btnListen
		btnListen = findViewById(R.id.btnListen) as FloatingActionButton
		//circle view
		circleListen1 = findViewById(R.id.circle1)
		circleListen2 = findViewById(R.id.circle2)
		circleListen3 = findViewById(R.id.circle3)
		//anim
		animHideBtnListen = loadAnim(R.anim.hide_btn_listen)
		animShowBtnListen = loadAnim(R.anim.show_btn_listen)

		animHideCircleListen1 = loadAnim(R.anim.hide_circle_listen1)
		animHideCircleListen2 = loadAnim(R.anim.hide_circle_listen2)
		animHideCircleListen3 = loadAnim(R.anim.hide_circle_listen3)
		animShowCircleListen1 = loadAnim(R.anim.show_circle_listen1)
		animShowCircleListen2 = loadAnim(R.anim.show_circle_listen2)
		animShowCircleListen3 = loadAnim(R.anim.show_circle_listen3)

		animCircleDown1 = loadAnim(R.anim.circle_down1)
		animCircleUp1 = loadAnim(R.anim.circle_up1)
		animCircleDown2 = loadAnim(R.anim.circle_down2)
		animCircleUp2 = loadAnim(R.anim.circle_up2)
		animCircleDown3 = loadAnim(R.anim.circle_down3)
		animCircleUp3 = loadAnim(R.anim.circle_up3)

		animShowTextListening = loadAnim(R.anim.show_text_listening)
		animHideTextListening = loadAnim(R.anim.hide_text_listening)
		//btn listcommand & setting
		btnListCommand = findViewById(R.id.btnListCommand) as FloatingActionButton
		btnSetting = findViewById(R.id.btnSetting) as FloatingActionButton
		//tvResulttemp
		tvListening = findViewById(R.id.tvResultTemp) as TextView
		//layoutResult(scrollView)
		layoutResult = findViewById(R.id.layout_result) as LinearLayout
		//layout circle listening
		layoutCircleListening = findViewById(R.id.layout_circle_listening) as LinearLayout
		//scrollview
		scrollView = findViewById(R.id.scrollView) as ScrollView
		//init mPresenter
		mPresenter = PresenterMain(this, this)
		//init datacommand
		dataCommand = mutableListOf(
				ItemCommand(R.drawable.ic_web_search, "Bảng xếp hạng VLeagua(coming soon)"),
				ItemCommand(R.drawable.ic_web_search, "Lịch thi đấu ngoại hạng Anh(coming soon)"),
				ItemCommand(R.drawable.ic_web_search, "Lập trình viên là gì"),
				ItemCommand(R.drawable.ic_translater, "Lập trình viên tiếng anh là gì"),
				ItemCommand(R.drawable.ic_calculator, "5 cộng 6 bằng bao nhiêu"),
				ItemCommand(R.drawable.ic_locate, "ICTU ở đâu"),
				ItemCommand(R.drawable.ic_web_search, "Kết quả xổ số miền bắc"),
				ItemCommand(R.drawable.ic_camera_flash, "Bật đèn Flash"),
				ItemCommand(R.drawable.ic_calendar, "Lịch vạn niên ngày 1 tháng 3 âm lịch(coming soon"),
				ItemCommand(R.drawable.ic_video, "Xem video của Ronaldo"),
				ItemCommand(R.drawable.ic_weather, "Thời tiết ở hà nam"),
				ItemCommand(R.drawable.ic_calendar, "Ngày 1 tháng 3 là bao nhiêu âm lịch(coming soon)"),
				ItemCommand(R.drawable.ic_call, "Gọi taxi Thái Nguyên"),
				ItemCommand(R.drawable.ic_web_search, "Xem tỉ giá ngoại tệ"),
				ItemCommand(R.drawable.ic_setting_light, "Tăng độ sáng màn hình lên một chút"),
				ItemCommand(R.drawable.ic_find_photo, "Tìm ảnh của soobin(coming soon)"),
				ItemCommand(R.drawable.ic_call, "Kiểm tra tài khoản khuyến mãi"),
				ItemCommand(R.drawable.ic_sms, "Gửi tin nhắn cho minh 5 giờ đi chạy bờ hồ"),
				ItemCommand(R.drawable.ic_contact, "Tìm số điện thoại của minh"),
				ItemCommand(R.drawable.ic_web_search, "Vào trang tinh tế"),
				ItemCommand(R.drawable.ic_locate, "Có nhà hàng nào gần đây không"),
				ItemCommand(R.drawable.ic_setting, "Bật wifi"),
				ItemCommand(R.drawable.ic_alarm, "Đặt báo thức lúc 6 giờ 10"),
				ItemCommand(R.drawable.ic_find_direction, "Tìm đường từ hà nam tới thái nguyên")
		)
	}

	private fun loadAnim(res: Int): Animation = AnimationUtils.loadAnimation(this, res)

	private fun initListener() {
		btnListen.setOnClickListener { onClickListen() }
		btnSetting.setOnClickListener { onClickSetting() }
		btnListCommand.setOnClickListener { onClickShowListCommand() }
		//listener animation
		animHideBtnListen.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(p0: Animation?) {

			}

			override fun onAnimationEnd(p0: Animation?) {
				mPresenter?.animOnListeningEnd()
			}

			override fun onAnimationStart(p0: Animation?) {

			}

		})
		animShowBtnListen.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(p0: Animation?) {

			}

			override fun onAnimationEnd(p0: Animation?) {
				mPresenter?.animOffListeningEnd()
			}

			override fun onAnimationStart(p0: Animation?) {

			}

		})
		animHideCircleListen1.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(p0: Animation?) {
				//
			}

			override fun onAnimationEnd(p0: Animation?) {
				mPresenter?.animHideCircleListenEnd()
			}

			override fun onAnimationStart(p0: Animation?) {
				//
			}

		})
		animShowCircleListen1.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(p0: Animation?) {
				//
			}

			override fun onAnimationEnd(p0: Animation?) {
				mPresenter?.animShowCircleListenEnd()
			}

			override fun onAnimationStart(p0: Animation?) {
				//
			}

		})
		//
		animCircleUp1.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(p0: Animation?) {
				//
			}

			override fun onAnimationEnd(p0: Animation?) {
				//code
				circleListen1.startAnimation(animCircleDown1)

			}

			override fun onAnimationStart(p0: Animation?) {
				//
			}

		})


		animCircleUp2.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(p0: Animation?) {
				//
			}

			override fun onAnimationEnd(p0: Animation?) {
				//code
				circleListen2.startAnimation(animCircleDown2)

			}

			override fun onAnimationStart(p0: Animation?) {
				//
			}

		})


		animCircleUp3.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(p0: Animation?) {
				//
			}

			override fun onAnimationEnd(p0: Animation?) {
				//code
				circleListen3.startAnimation(animCircleDown3)
				//circleListen1.startAnimation(animCircleUp1)
			}

			override fun onAnimationStart(p0: Animation?) {
				//
			}

		})
		animCircleDown3.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(p0: Animation?) {
				//
			}

			override fun onAnimationEnd(p0: Animation?) {
				//code
				mPresenter?.updateColorCircle()

			}

			override fun onAnimationStart(p0: Animation?) {
				//
			}

		})

		animHideTextListening.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(p0: Animation?) {
				//
			}

			override fun onAnimationEnd(p0: Animation?) {
				//hide text listening
				tvListening.visibility = View.INVISIBLE
			}

			override fun onAnimationStart(p0: Animation?) {
				//
			}

		})
	}

	private fun onClickSetting() {
		//TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	private fun onClickShowListCommand() {

		//initView
		val layout: View = layoutInflater.inflate(R.layout.layout_can_help, null)
		val listCanHelp: ListView = layout.findViewById(R.id.list_can_help)
		listCanHelp.adapter = getAdapterListViewCommand()

		val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)

		alertBuilder.setView(layout)
		val alert = alertBuilder.create()

		listCanHelp.setOnItemClickListener { _, _, position, _ ->
			alert.cancel()
			mPresenter!!.onClickItemCommand(dataCommand[position])
		}

		alert.show()

	}

	private fun getAdapterListViewCommand(): AdapterCommandList {
		return AdapterCommandList(this, R.layout.item_can_help, dataCommand)
	}

	private fun onClickListen() {
		mPresenter?.onClickListen(!isListen)
	}


	private fun checkFirstOpen() {
		val sharedPre = SharedPreference(this, "firstOpenApp")
		if (sharedPre.get("isFirst", true)) {
			startActivity(Intent(this, IntroActivity::class.java))
			finish()
		}
		sharedPre.put("isFirst", false)
	}

	override fun show1() {
		toast("show1")
	}

	override fun show2() {
		toast("show2")
	}

	override fun show3() {
		toast("show3")
	}

	//override method TODO

	override fun startAnimationListening() {
		isListen = true
		btnListen.startAnimation(animHideBtnListen)
	}

	override fun stopAnimationListening() {
		isListen = false
		btnListen.clearAnimation()
	}

	override fun setListening(b: Boolean) {
		isListen = b
	}

	override fun showTextListening() {
		tvListening.visibility = View.VISIBLE
		tvListening.startAnimation(animShowTextListening)
	}

	override fun hideTextListening() {
		tvListening.startAnimation(animHideTextListening)
		//an tv xong reset lai ket qua
		tvListening.text = "Listening.."
	}

	override fun addTextRequestToLayout(data: String?) {
		tvListening.text = data
		tvListening.visibility = View.INVISIBLE /////////////
		addMyTextToLayout(data)

	}

	private fun addMyTextToLayout(data: String?) {
		layoutResult.addView(MyText(this).setText(data!!))
		scrollView.scroll()
	}

	private fun addBotTextToLayout(data: String?) {
		layoutResult.addView(BotText(this).setText(data!!))
		scrollView.scroll()
	}

	private fun addBotButtonToLayout(data: String?) {
		layoutResult.addView(BotButton(this).setText("Tìm trên web.").setData(data!!))
		scrollView.scroll()
	}

	override fun setTextListening(data: String?) {
		if (data!!.isNotEmpty()) tvListening.text = data
	}

	override fun onStart() {
		super.onStart()
		mPresenter?.onStart()
//
//		googleApiClient?.connect()

	}

	override fun onStop() {
//		LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
//
//		googleApiClient?.disconnect()

		mPresenter?.onStop()
		super.onStop()

	}

	override fun onPause() {
		super.onPause()
		mPresenter?.onPause()
	}

	override fun hideBtnListen() {
		btnListen.visibility = View.INVISIBLE
		layoutCircleListening.visibility = View.VISIBLE
	}

	override fun startShowAnimCircleListen() {
		circleListen1.startAnimation(animShowCircleListen1)
		circleListen2.startAnimation(animShowCircleListen2)
		circleListen3.startAnimation(animShowCircleListen3)
	}

	override fun startHideAnimCircleListen() {
		circleListen1.startAnimation(animHideCircleListen1)
		circleListen2.startAnimation(animHideCircleListen2)
		circleListen3.startAnimation(animHideCircleListen3)
	}

	override fun hideLayoutCircleListen() {
		layoutCircleListening.visibility = View.INVISIBLE
	}

	override fun showBtnListen() {
		isListen = false
		btnListen.visibility = View.VISIBLE
		btnListen.startAnimation(animShowBtnListen)
	}

	override fun getStateCircleColorCurrent(): Int = stateCircleColor

	override fun updateStateCircleColor(currentState: Int) {
		stateCircleColor = currentState
	}

	override fun updateColorCircle(circle_listening1: Int, circle_listening2: Int, circle_listening3: Int) {

		circleListen1.setBackgroundResource(circle_listening1)
		circleListen2.setBackgroundResource(circle_listening2)
		circleListen3.setBackgroundResource(circle_listening3)
		Log.e("updatecolorcircle", "ok!")


	}

	override fun clearAnimationUpDown() {
		circleListen1.clearAnimation()
		circleListen2.clearAnimation()
		circleListen3.clearAnimation()
	}

	override fun startAnimationCircleUpDown() {
		circleListen1.startAnimation(animCircleUp1)
		circleListen2.startAnimation(animCircleUp2)
		circleListen3.startAnimation(animCircleUp3)
	}

	override fun addTiGiaToLayout(result: ItemTiGia) {
		Log.e("size:", result.items.size.toString())
		layoutResult.addView(LayoutTiGia(this).setData(result))
		scrollView.scroll()
	}

	//override thoi tiet
	override fun setItemThoiTiet(result: ItemThoiTiet) {
		itemThoiTiet = result
	}

	override fun getItemThoiTiet7Ngay(): ItemThoiTiet7Ngay? {
		return itemThoiTiet7Ngay
	}

	override fun addThoiTietToLayout() {
		layoutResult.addView(LayoutThoiTiet(this).setData(itemThoiTiet, itemThoiTiet7Ngay))
		scrollView.scroll()
	}

	override fun setItemThoiTiet7Ngay(result: ItemThoiTiet7Ngay) {
		itemThoiTiet7Ngay = result
	}

	override fun getItemThoiTiet(): ItemThoiTiet? {
		return itemThoiTiet
	}

	//override kqxs
	override fun showKqxs(result: MutableList<String>) {
		layoutResult.addView(LayoutXoSo(this).setData(result))
		scrollView.scroll()
	}

	//TODO override function after handle request

	override fun showReplyNotKnowLanguage(keywordSearch: String, ngonNgu: String) {
		addBotTextToLayout("Xin lỗi! mình không biết ngôn ngữ này :( ($keywordSearch trong tiếng $ngonNgu)")
		addBotButtonToLayout("$keywordSearch trong tiếng $ngonNgu")
	}

	override fun showNoResultContact() {
		addBotTextToLayout("Không có ai trong danh bạ của bạn có tên như vậy cả :(")
	}

	override fun showContacts(numberPhone: MutableList<ItemContact>) {

		//initView
		val layout: View = layoutInflater.inflate(R.layout.layout_contact, null)
		val listContact: ListView = layout.findViewById(R.id.list_contact)
		listContact.adapter = AdapterContact(this, R.layout.item_contact, numberPhone)

		val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)

		alertBuilder.setView(layout)
		val alert = alertBuilder.create()

		listContact.setOnItemClickListener { _, _, position, _ ->
			alert.cancel()
			mPresenter!!.onClickItemContact(numberPhone[position])
		}

		alert.show()
	}

	override fun showKoTheChia0() {
		addBotTextToLayout("Phép tính của bạn bị sai vì không thể chia bất cứ số nào cho số 0")
	}

	override fun showResultCalculator(kq: Float, pheptinh: String) {
		addBotTextToLayout("$pheptinh bằng $kq")
	}

	override fun showCantCalculator(pheptinh: String) {
		addBotTextToLayout("Xin lỗi! mình không thể tính được phép tính này: $pheptinh :(")
		addBotButtonToLayout(pheptinh)
	}

	override fun showNoResultKqxsFor(mien: String?) {
		addBotTextToLayout("Xin lỗi! mình không có kết quả cho vùng miền của bạn:( .Thử tìm kiếm trên web ?.")
		addBotButtonToLayout("kết quả xổ số $mien")
	}

	override fun showKoHieu(data: String) {
		addBotTextToLayout("Xin lỗi! mình không hiểu ý của bạn. Thử tìm nó trên web ?.")
		addBotButtonToLayout(data)
	}

	override fun showResultTranslate(result: String) {
		addBotTextToLayout("Nghĩa của câu đó là: $result")
	}

	override fun updateWindowAfterSetBrightNess(brightness: Int) {
		//Get the current window attributes
		val layoutpars = window.attributes
		//Set the brightness of this window
		layoutpars.screenBrightness = brightness / 255.toFloat()
		//Apply attribute changes to this window
		window.attributes = layoutpars
	}

	//TODO add reply

	override fun showReplyAfterSetBrightNess(brightness: Int) {
		addBotTextToLayout("Độ sáng màn hình hiện tại là ${brightness / 255.0f * 100}%")
	}

	override fun showReplyOpenedMap() {
		addBotTextToLayout("Đã mở bản đồ :)")
	}

	override fun showReplySetedAlarm(hour: Int, min: Int) {
		addBotTextToLayout("Đã đặt báo thức lúc $hour giờ $min phút :)")
	}

	override fun showReplyWifiOn() {
		addBotTextToLayout("Đã bật wifi :)")
	}

	override fun showReplyWifiOff() {
		addBotTextToLayout("Đã tắt wifi :)")
	}

	override fun showReplyOpenedUrl() {
		addBotTextToLayout("Đã mở trình duyệt :)")
	}

	override fun showReplyOpenedSms() {
		addBotTextToLayout("Đã mở ứng dụng nhắn tin :)")
	}

	override fun showReplyCallUSSD(value: String) {
		addBotTextToLayout("Đã quay số $value")
	}

	override fun showReplyCalled(number: String) {
		addBotTextToLayout("Đã gọi tới số $number")
	}

	override fun showReplyOpenedYoutube() {
		addBotTextToLayout("Đã mở Youtube :)")
	}

	override fun showReplyFlashOff() {
		addBotTextToLayout("Đã tắt đèn flash :)")
	}

	override fun showReplyFlashOn() {
		addBotTextToLayout("Đã bật đèn flash :)")
	}
}

private fun ScrollView.scroll() {
	this.post { this@scroll.fullScroll(android.widget.ScrollView.FOCUS_DOWN) }
}

private fun Activity.toast(s: String) {
	Toast.makeText(this@toast, s, Toast.LENGTH_LONG).show()
}
