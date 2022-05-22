package com.hazz.kotlinmvp.ui.dialog

import android.text.TextUtils
import android.util.Log
import android.view.ViewGroup
import com.hazz.kotlinmvp.R
import com.hazz.kotlinmvp.base.BaseDialogFragment
import com.hazz.kotlinmvp.mvp.contract.ResetContract
import com.hazz.kotlinmvp.mvp.presenter.ResetPresenter
import com.hazz.kotlinmvp.showToast
import kotlinx.android.synthetic.main.activity_register.password_view
import kotlinx.android.synthetic.main.activity_register.second_password_view
import kotlinx.android.synthetic.main.reset_pwd_dialog_layout.*

class ResetPwdDialog: BaseDialogFragment(),ResetContract.View {
    val presenter by lazy {
        ResetPresenter()
    }

    var loadingMore = true

    companion object{
        fun getInstance(): ResetPwdDialog{
            val fragment = ResetPwdDialog()
            return fragment
        }
    }


    override fun getLayoutId() = R.layout.reset_pwd_dialog_layout

    override fun initView() {
        presenter.attachView(this)
        initMultipleView()
        confirm_button.setOnClickListener {
            if (TextUtils.isEmpty(password_view.text) || TextUtils.isEmpty(second_password_view.text)) {
                showToast("请填入密码")
            } else if (password_view.text.toString() != second_password_view.text.toString()) {
                showToast("请保证两次密码相同")
            } else {
                if (loadingMore) {
                    loadingMore = false
                    presenter.queryResetResult(password_view.text.toString())
                }
            }
        }
    }

    fun initMultipleView(){
        /*val params = multipleStatusView.layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = 800
        multipleStatusView.layoutParams = params*/
        multipleStatusView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        multipleStatusView.layoutParams.height = 900
    }

    override fun resetSuccess() {
        loadingMore = true
        dismiss()
    }

    override fun resetError() {
        loadingMore = true
        showToast("请联系管理员开启服务器")
    }

    override fun resetFailure() {
        loadingMore = true
        showToast("更改失败，请重试")
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }
}