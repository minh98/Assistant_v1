package com.example.minh98.assistant_v1.viewsLayout.ThoiTiet

/**
 * Created by minh98 on 12/09/2017.
 */
data class Coord(val lon: Float, val lat: Float)

data class Weather(val id: Int, val main: String, val description: String, val icon: String)

data class Main(val temp: Float, val pressure: Float, val humidity: Float, val temp_min: Float, val temp_max: Float)

data class Wind(val speed: Float, val deg: Float)

data class Clouds(val all: Float)

data class Sys(val type: Int, val id: Int, val message: Float, val country: String, val sunrise: Float, val sunset: Float)

class ItemThoiTiet {
	var coord: Coord? = null
	var weather = mutableListOf<Weather>()
	var base: String = ""
	var main: Main? = null
	var visibility: Float = 0f
	var wind: Wind? = null
	var clouds: Clouds? = null
	var dt: Long = 0
	var sys: Sys? = null
	var id: Int = 0
	var name: String = ""
	var cod: Int = 0
}