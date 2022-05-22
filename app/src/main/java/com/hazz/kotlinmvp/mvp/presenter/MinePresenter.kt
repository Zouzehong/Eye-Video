package com.hazz.kotlinmvp.mvp.presenter

import com.hazz.kotlinmvp.UserManager
import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.mvp.contract.MineContract
import com.hazz.kotlinmvp.mvp.model.MineModel
import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MinePresenter : BasePresenter<MineContract.View>(), MineContract.Presenter {
    val model by lazy {
        MineModel()
    }
    override fun queryLogoutResult() {
        /*MineModel().getLogoutResult().enqueue(
                object : Callback<Any> {
                    override fun onResponse(p0: Call<Any>, p1: Response<Any>) {

                    }

                    override fun onFailure(p0: Call<Any>, p1: Throwable) {

                    }

                }
        )*/
    }

}