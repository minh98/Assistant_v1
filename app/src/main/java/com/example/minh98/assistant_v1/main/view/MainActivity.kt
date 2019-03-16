package com.example.minh98.assistant_v1.main.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
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
import java.lang.Exception
import java.util.*


class MainActivity : AppCompatActivity(), IViewMain {
    override fun sendToTurnSingleLed(isOn: Boolean, index: Int) {
        addBotTextToLayout("Đang gửi tín hiệu để ${if(isOn){"bật"}else{"tắt"}} đèn $index")
    }

    override fun sendToTurnMultiLed(isOn: Boolean) {
        addBotTextToLayout("Đang gửi tín hiệu để ${if(isOn){"bật"}else{"tắt"}} cả hai đèn ")
    }

    //button
    private lateinit var btnListen: FloatingActionButton
    private lateinit var btnListCommand: FloatingActionButton
    private lateinit var btnSetting: FloatingActionButton
    //class
    private var mPresenter: PresenterMain? = null

    private lateinit var animShowTextListening: Animation
    private lateinit var animHideTextListening: Animation

    //layout
    private lateinit var layoutResult: LinearLayout
    private lateinit var scrollView: ScrollView

    //data
    private lateinit var dataCommand: MutableList<ItemCommand>
    private var itemThoiTiet: ItemThoiTiet? = null
    private var itemThoiTiet7Ngay: ItemThoiTiet7Ngay? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            val param: RelativeLayout.LayoutParams = (findViewById<ScrollView>(R.id.scrollView)).layoutParams as RelativeLayout.LayoutParams
            val height = 24 * resources.displayMetrics.density
            param.topMargin = height.toInt()
            (findViewById<ScrollView>(R.id.scrollView)).layoutParams = param
        }


//        checkFirstOpen()
        initView()

        //test :))
        layoutResult.addView(BotText(this).setText("Xin chào :) Bạn có thể hỏi tôi bất cứ khi nào muốn"))
        initListener()

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED

        ) {
            //run time request permission
            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.INTERNET,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.WRITE_SETTINGS,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.CAMERA,
                    Manifest.permission.SET_ALARM,
                    Manifest.permission.ACCESS_FINE_LOCATION

            ), 99)
        }
    }

    private fun initView() {
        //btnListen
        btnListen = findViewById(R.id.btnListen)

        animShowTextListening = loadAnim(R.anim.show_text_listening)
        animHideTextListening = loadAnim(R.anim.hide_text_listening)
        //btn listcommand & setting
        btnListCommand = findViewById(R.id.btnListCommand)
        btnSetting = findViewById(R.id.btnSetting)
        //layoutResult(scrollView)
        layoutResult = findViewById(R.id.layout_result)
        //layout circle listening
        //scrollview
        scrollView = findViewById(R.id.scrollView)
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

    private fun getAdapterListViewCommand(): AdapterCommandList =
            AdapterCommandList(this, R.layout.item_can_help, dataCommand)

    private fun onClickListen() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Mình đang nghe nè")
        }
        try {
            startActivityForResult(intent, 100)
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    Log.e("text is ", results[0])
                    mPresenter?.handleRequest(results[0])
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mPresenter?.onStop()
    }


    private fun checkFirstOpen() {
        val sharedPre = SharedPreference(this, "firstOpenApp")
        if (sharedPre.get("isFirst", true)) {
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }
        sharedPre.put("isFirst", false)
    }

    override fun addTextRequestToLayout(data: String?) {
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

    override fun addTiGiaToLayout(result: ItemTiGia) {
        layoutResult.addView(LayoutTiGia(this).setData(result))
        scrollView.scroll()
    }

    //override thoi tiet
    override fun setItemThoiTiet(result: ItemThoiTiet) {
        itemThoiTiet = result
    }

    override fun getItemThoiTiet7Ngay(): ItemThoiTiet7Ngay? = itemThoiTiet7Ngay

    override fun addThoiTietToLayout() {
        layoutResult.addView(LayoutThoiTiet(this).setData(itemThoiTiet, itemThoiTiet7Ngay))
        scrollView.scroll()
    }

    override fun setItemThoiTiet7Ngay(result: ItemThoiTiet7Ngay) {
        itemThoiTiet7Ngay = result
    }

    override fun getItemThoiTiet(): ItemThoiTiet? = itemThoiTiet


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
