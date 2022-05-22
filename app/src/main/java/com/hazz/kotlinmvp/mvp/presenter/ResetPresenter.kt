package com.hazz.kotlinmvp.mvp.presenter

import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.mvp.contract.ResetContract
import com.hazz.kotlinmvp.mvp.model.ResetModel
import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPresenter: BasePresenter<ResetContract.View>(), ResetContract.Presenter {
    val model by lazy {
        ResetModel()
    }

    override fun queryResetResult(password: String) {
        mRootView?.showLoading()
        model.getResetResult(password).enqueue(
                object : Callback<StatusBean> {
                    override fun onResponse(p0: Call<StatusBean>, response: Response<StatusBean>) {
                        mRootView?.dismissLoading()
                        val body = response.body()
                        if (body == null) {
                            mRootView?.resetError()
                        } else {
                            if (body.code == 0) {
                                mRootView?.resetSuccess()
                            } else if (body.code == 500) {
                                mRootView?.resetFailure()
                            }
                        }
                    }

                    override fun onFailure(p0: Call<StatusBean>, p1: Throwable) {

                    }
                }
        )
    }
}