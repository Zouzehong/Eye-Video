package com.hazz.kotlinmvp.mvp.presenter

import com.hazz.kotlinmvp.UserManager
import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.mvp.contract.RegisterContract
import com.hazz.kotlinmvp.mvp.model.RegisterModel
import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenter: BasePresenter<RegisterContract.View>(), RegisterContract.Presenter{
    private val model by lazy {
        RegisterModel()
    }
    override fun queryRegisterResult(username: String, password: String) {
         mRootView?.showLoading()
         model.getRegisterResult(username,password).enqueue(
                 object : Callback<StatusBean>{
                     override fun onResponse(call: Call<StatusBean>, response: Response<StatusBean>) {
                         mRootView?.dismissLoading()
                         val body = response.body()
                         if (body == null) {
                             mRootView?.registerError()
                         } else {
                             if (body.code == 0) {
                                 val cookies = response.headers().values("Set-Cookie")
                                 val session = cookies[0]
                                 val sessionId = session.substring(0, session.indexOf(";"))
                                 UserManager.setUserStatus(username, password, sessionId, true)
                                 mRootView?.registerSuccess()
                             } else if (body.code == 500) {
                                 mRootView?.registerFailure()
                             }
                         }
                     }

                     override fun onFailure(call: Call<StatusBean>, e: Throwable) {

                     }

                 }
         )
    }
}