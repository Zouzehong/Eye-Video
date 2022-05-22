package com.hazz.kotlinmvp.mvp.model


import android.util.Log
import com.hazz.kotlinmvp.api.UrlConstant
import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import com.hazz.kotlinmvp.net.RetrofitManager
import retrofit2.Call


class LoginModel {
    //191459352972
    companion object {
        const val TAG = "LoginModel"
        const val url = UrlConstant.ROOT_URL + "/login"
    }

    fun getLoginResult(username: String,password: String) : Call<StatusBean> {
        Log.d(TAG,"username = $username, password = $password")
        return RetrofitManager.ruoyi.getLoginInfo(username,password,"false")
    }

}
