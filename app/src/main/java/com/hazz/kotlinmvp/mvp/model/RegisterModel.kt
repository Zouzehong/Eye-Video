package com.hazz.kotlinmvp.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import com.hazz.kotlinmvp.net.RetrofitManager
import retrofit2.Call

class RegisterModel {
    fun getRegisterResult(loginName: String,password: String): Call<StatusBean> {
        return RetrofitManager.ruoyi.getRegisterInfo(loginName,password)
    }
}