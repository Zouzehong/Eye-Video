package com.hazz.kotlinmvp.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

interface RegisterContract {
    interface View:IBaseView{
        fun registerSuccess()
        fun registerFailure()
        fun registerError()
    }
    interface Presenter:IPresenter<View> {
        fun queryRegisterResult(username: String,password: String)
    }
}