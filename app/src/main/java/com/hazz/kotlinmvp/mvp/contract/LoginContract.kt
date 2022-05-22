package com.hazz.kotlinmvp.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

interface LoginContract {
    interface View : IBaseView {

        fun loginSuccess()

        fun loginFailure()

        fun loginError()
    }

    interface Presenter : IPresenter<View> {
        fun queryUserAndPassword(user: String, password: String)
    }
}