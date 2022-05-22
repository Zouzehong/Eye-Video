package com.hazz.kotlinmvp.mvp.model

import com.hazz.kotlinmvp.UserManager
import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import com.hazz.kotlinmvp.net.RetrofitManager
import retrofit2.Call

class ResetModel {
    fun getResetResult(password: String) : Call<StatusBean> {
        return RetrofitManager.ruoyi.getResetInfo(UserManager.sessionId, UserManager.username,password)
    }
}