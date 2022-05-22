package com.hazz.kotlinmvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.hazz.kotlinmvp.R
import com.hazz.kotlinmvp.UserManager
import com.hazz.kotlinmvp.base.BaseFragment
import com.hazz.kotlinmvp.mvp.contract.MineContract
import com.hazz.kotlinmvp.mvp.presenter.MinePresenter
import com.hazz.kotlinmvp.showToast
import com.hazz.kotlinmvp.ui.activity.LoginActivity
import com.hazz.kotlinmvp.ui.activity.ProfileHomePageActivity
import com.hazz.kotlinmvp.ui.activity.WatchHistoryActivity
import com.hazz.kotlinmvp.ui.dialog.ResetPwdDialog
import com.hazz.kotlinmvp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : BaseFragment(),MineContract.View,View.OnClickListener {


    private lateinit var mTitle:String

    private val presenter by lazy {
        MinePresenter()
    }


    companion object {
        fun getInstance(title:String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }



    override fun getLayoutId(): Int= R.layout.fragment_mine

    override fun initView() {
        presenter.attachView(this)
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }

        tv_view_homepage.setOnClickListener(this)
        iv_avatar.setOnClickListener(this)
        iv_about.setOnClickListener(this)
        tv_sign_out.setOnClickListener(this)
        tv_reset.setOnClickListener(this)

        tv_collection.setOnClickListener(this)
        tv_comment.setOnClickListener(this)

        tv_mine_message.setOnClickListener(this)
        tv_mine_attention.setOnClickListener(this)
        tv_mine_cache.setOnClickListener(this)
        tv_watch_history.setOnClickListener(this)
        tv_feedback.setOnClickListener(this)


    }

    override fun lazyLoad() {

    }

    override fun onResume() {
        super.onResume()
        Log.d("Status","onResume status = ${UserManager.status}")
        if (!UserManager.status) {
            tv_nickname.text = requireActivity().resources.getString(R.string.user_not_login)
            tv_sign_out.visibility = ViewGroup.GONE
            tv_reset.visibility = ViewGroup.GONE
        }
        else {
            tv_nickname.text = UserManager.username
            tv_sign_out.visibility = ViewGroup.VISIBLE
            tv_reset.visibility = ViewGroup.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        v?.apply {
            when (id) {
                R.id.iv_avatar, R.id.tv_view_homepage -> {
                    Log.d("Status","onClick status = ${UserManager.status}")
                    if(UserManager.status) {
                        val intent = Intent(activity, ProfileHomePageActivity::class.java)
                        startActivity(intent)
                    }else{
                        startActivity(Intent(activity, LoginActivity::class.java))
                    }
                }
                R.id.iv_about -> {
                }
                R.id.tv_collection -> showToast("收藏")
                R.id.tv_comment -> showToast("评论")
                R.id.tv_mine_message -> showToast("我的消息")
                R.id.tv_mine_attention -> showToast("我的关注")
                R.id.tv_mine_attention -> showToast("我的缓存")
                R.id.tv_watch_history -> startActivity(Intent(activity,WatchHistoryActivity::class.java))
                R.id.tv_feedback -> showToast("意见反馈")
                R.id.tv_sign_out -> showSignOutDialog()
                R.id.tv_reset -> ResetPwdDialog.getInstance().show(childFragmentManager)
            }
        }
    }

    fun showSignOutDialog(){
        AlertDialog.Builder(requireContext()).setTitle("请确认是否注销当前用户").setPositiveButton("确认"){
            dialog, which ->
            presenter.queryLogoutResult()
            UserManager.clearUserStatus()
            onResume()
        }.setNegativeButton("取消"){
            dialog, which ->  { }
        }.show()
    }



    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


}