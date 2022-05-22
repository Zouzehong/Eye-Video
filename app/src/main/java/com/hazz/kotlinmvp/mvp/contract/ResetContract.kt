package com.hazz.kotlinmvp.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter

interface ResetContract {
    interface View : IBaseView{
       fun resetSuccess()
       fun resetError()
       fun resetFailure()
    }
    interface Presenter:IPresenter<View>{
      fun queryResetResult(password: String)
    }
}