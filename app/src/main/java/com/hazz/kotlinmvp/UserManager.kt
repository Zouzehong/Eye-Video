package com.hazz.kotlinmvp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import com.hazz.kotlinmvp.net.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object UserManager {
    var status = false
    var username = ""
    var password = ""
    var sessionId = ""
    private lateinit var sp:SharedPreferences

    fun getUserStatus(context: Context){
        sp = context.getSharedPreferences("data",0)
        username = sp.getString("username","")
        status = sp.getBoolean("status",false)
        password = sp.getString("password","")
        sessionId = sp.getString("sessionId", "")
    }


    fun setUserStatus(username: String,password: String,sessionId:String,status: Boolean){
        this.username = username
        this.status = status
        this.password = password
        this.sessionId = sessionId
        saveUserInfo()
        Log.d("Status","setUserStatus status = $status")
    }

    fun saveUserInfo(){
        val editor = sp.edit()
        editor.putBoolean("status", status)
        editor.putString("username", username)
        editor.putString("password", password)
        editor.putString("sessionId", sessionId)
        editor.apply()
    }


    fun clearUserStatus(){
        setUserStatus("","","",false)
    }


}