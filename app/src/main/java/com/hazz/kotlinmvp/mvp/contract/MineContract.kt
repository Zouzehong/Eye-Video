package com.hazz.kotlinmvp.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

interface MineContract {
    interface View: IBaseView{

    }
    interface Presenter: IPresenter<View>{
        fun queryLogoutResult()
    }
}