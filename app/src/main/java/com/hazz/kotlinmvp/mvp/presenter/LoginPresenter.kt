package com.hazz.kotlinmvp.mvp.presenter

import android.util.Log
import com.hazz.kotlinmvp.UserManager
import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.mvp.contract.LoginContract
import com.hazz.kotlinmvp.mvp.model.LoginModel
import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {
    val loginModel by lazy {
        LoginModel()
    }


    override fun queryUserAndPassword(username: String, password: String) {
        mRootView?.showLoading()
        Log.d("LoginPresenter", "username = $username password = ${password}")
        loginModel.getLoginResult(username, password)
                .enqueue(object : Callback<StatusBean> {
                    override fun onResponse(call: Call<StatusBean>, response: Response<StatusBean>) {
                        mRootView?.dismissLoading()
                        val body = response.body()
                        if (body == null) {
                            mRootView?.loginError()
                        } else {
                            if (body.code == 0) {
                                val cookies = response.headers().values("Set-Cookie")
                                val session = cookies[0]
                                val sessionId = session.substring(0, session.indexOf(";"))
                                UserManager.setUserStatus(username, password, sessionId, true)
                                mRootView?.loginSuccess()
                            } else if (body.code == 500) {
                                mRootView?.loginFailure()
                            }
                        }
                    }

                    override fun onFailure(call: Call<StatusBean>, e: Throwable) {
                        //出现异常情况

                    }

                }
                )
    }

}