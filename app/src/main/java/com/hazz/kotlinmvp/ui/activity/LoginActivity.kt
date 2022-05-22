package com.hazz.kotlinmvp.ui.activity


import android.content.Intent
import com.hazz.kotlinmvp.R
import com.hazz.kotlinmvp.base.BaseActivity
import com.hazz.kotlinmvp.mvp.contract.LoginContract
import com.hazz.kotlinmvp.mvp.presenter.LoginPresenter
import com.hazz.kotlinmvp.showToast
import com.hazz.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activtiy_login.*
import kotlinx.android.synthetic.main.activtiy_login.multipleStatusView
import kotlinx.android.synthetic.main.activtiy_login.toolbar



class LoginActivity : BaseActivity(),LoginContract.View{
    val presenter by lazy {
        LoginPresenter()
    }

    override fun layoutId(): Int {
        return R.layout.activtiy_login
    }

    var loadingMore = true

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)
        toolbar.setNavigationOnClickListener { finish() }
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        confirm_btn.setOnClickListener {
            val userName = account_view.text.toString()
            val password = password_view.text.toString()
            if(userName.isEmpty() && password.isEmpty()){
                 showToast("请输入账号和密码")
            }else if(userName.isEmpty()){
                 showToast("请输入账号")
            }else if(password.isEmpty()){
                 showToast("请输入密码")
            }else {
                if(loadingMore) {
                    loadingMore = false
                    presenter.queryUserAndPassword(userName, password)
                }
            }
        }

        register_btn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun start() {

    }

    override fun loginSuccess() {
        loadingMore = true
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun loginFailure() {
        loadingMore = true
        showToast("请检查您的账号和密码")
    }

    override fun loginError() {
        loadingMore = true
        showToast("请联系管理员开启服务器")
    }


    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}