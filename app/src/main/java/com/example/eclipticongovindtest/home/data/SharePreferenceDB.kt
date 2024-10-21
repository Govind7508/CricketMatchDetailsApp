package com.example.eclipticongovindtest.home.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object SharePreferenceDB  {
    private lateinit var mSharedPref: SharedPreferences

     fun init(context: Context) {
        mSharedPref = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    fun setWelcomeState(welComeState: Boolean,context: Context){
        init(context)
        val editor = mSharedPref.edit()
        editor.putBoolean("state", welComeState)
        editor.commit()
    }


    fun getWelcomeState(context: Context): Boolean? {
        init(context)
        return mSharedPref.getBoolean("state", false)

    }
    fun setPhoneNumber(username: String,context: Context){
        init(context)
        val editor = mSharedPref.edit()
        editor.putString("PhoneNumber", username)
        editor.commit()
    }


    fun getPhoneNumber(context: Context): String? {
        init(context)
        return mSharedPref.getString("PhoneNumber", "")

    }
  fun setAuthCode(username: String,context: Context){
        init(context)
        val editor = mSharedPref.edit()
        editor.putString("code", username)
        editor.commit()
    }


    fun getAuthCode(context: Context): String? {
        init(context)
        return mSharedPref.getString("code", "")

    }

    fun setSubmitState(dashboard: Boolean,context: Context){
        init(context)
        val editor = mSharedPref.edit()
        editor.putBoolean("code", dashboard)
        editor.commit()
    }


    fun getSubmitState(context: Context): Boolean? {
        init(context)
        return mSharedPref.getBoolean("code",false)

    }



}