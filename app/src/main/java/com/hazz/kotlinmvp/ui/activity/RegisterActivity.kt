package com.hazz.kotlinmvp.ui.activity

import android.content.Intent
import com.hazz.kotlinmvp.R
import com.hazz.kotlinmvp.base.BaseActivity
import com.hazz.kotlinmvp.mvp.contract.RegisterContract
import com.hazz.kotlinmvp.mvp.presenter.RegisterPresenter
import com.hazz.kotlinmvp.showToast
import com.hazz.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.account_view
import kotlinx.android.synthetic.main.activity_register.confirm_btn
import kotlinx.android.synthetic.main.activity_register.multipleStatusView
import kotlinx.android.synthetic.main.activity_register.password_view
import kotlinx.android.synthetic.main.activity_register.toolbar


class RegisterActivity: BaseActivity(),RegisterContract.View{
    val presenter by lazy {
        RegisterPresenter()
    }

    var loadingMore = true

    override fun layoutId(): Int {
        return R.layout.activity_register
    }

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)
        toolbar.setNavigationOnClickListener { finish() }
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        confirm_btn.setOnClickListener {
            val username = account_view.text.toString()
            val password = password_view.text.toString()
            val secondPassword = second_password_view.text.toString()
            if(username.isEmpty() && password.isEmpty()){
                showToast("请输入账号和密码")
            }else if(username.isEmpty()){
                showToast("请输入账号")
            }else if(password.isEmpty()) {
                showToast("请输入密码")
            }else if(password != secondPassword)
                showToast("两次密码输入不一致")
            else {
                if(loadingMore) {
                    loadingMore = false
                    presenter.queryRegisterResult(username, password)
                }
            }
        }
    }

    override fun start() {

    }

    override fun registerSuccess() {
         loadingMore = true
         startActivity(Intent(this,MainActivity::class.java))
    }

    override fun registerFailure() {
         loadingMore = true
         showToast("该用户名已经被注册")
    }

    override fun registerError() {
        loadingMore = true
        showToast("请联系管理员开启服务器")
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }


}