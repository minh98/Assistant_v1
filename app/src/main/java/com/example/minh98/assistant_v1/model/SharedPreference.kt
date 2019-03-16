package com.example.minh98.assistant_v1.model

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by minh98 on 07/09/2017.
 */
class SharedPreference(context:Context,key:String) {
	private val sharedPreference=context.getSharedPreferences(key,Context.MODE_PRIVATE)
	private lateinit var editor:SharedPreferences.Editor

	fun <T>put(key: String,value:T){
		editor=sharedPreference.edit()
		with(editor){
			when(value){
				is String->putString(key,"")
				is Int->putInt(key,-1)
				is Float->putFloat(key,-1.0f)
				is Long->putLong(key,-1L)
				is Boolean->putBoolean(key,false)
				else -> null
			}
			apply()
		}
	}

	fun <T>get(key:String,default:T):T{

		return when(default){
			is String->sharedPreference.getString(key,default)
			is Int->sharedPreference.getInt(key,default)
			is Float->sharedPreference.getFloat(key,default)
			is Long->sharedPreference.getLong(key,default)
			is Boolean->sharedPreference.getBoolean(key,default)
			else -> null
		} as T
	}

}