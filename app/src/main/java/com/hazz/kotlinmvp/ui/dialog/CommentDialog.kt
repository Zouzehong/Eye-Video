package com.hazz.kotlinmvp.ui.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kotlinmvp.R
import com.hazz.kotlinmvp.UserManager
import com.hazz.kotlinmvp.base.BottomDialogFragment
import com.hazz.kotlinmvp.mvp.contract.CommentContract
import com.hazz.kotlinmvp.mvp.model.bean.CommentBean
import com.hazz.kotlinmvp.mvp.presenter.CommentPresenter
import com.hazz.kotlinmvp.showToast
import com.hazz.kotlinmvp.ui.adapter.CommentAdapter
import kotlinx.android.synthetic.main.comment_dialog_layout.*
import kotlinx.android.synthetic.main.comment_dialog_layout.mRecyclerView
import kotlinx.android.synthetic.main.comment_dialog_layout.multipleStatusView

class CommentDialog : BottomDialogFragment(), CommentContract.View {
    companion object {
        const val URL = "URL"
        fun getInstance(url: String): CommentDialog {
            val fragment = CommentDialog()
            val bundle = Bundle()
            bundle.putString(URL, url)
            fragment.arguments = bundle
            return fragment
        }
    }

    var loadingMore = true

    private val presenter by lazy {
        CommentPresenter()
    }



    override fun getLayoutId() = R.layout.comment_dialog_layout

    override fun initView() {
        presenter.attachView(this)
        mRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mRecyclerView.adapter = CommentAdapter(requireContext(), ArrayList())
        arguments?.let { bundle ->
            presenter.getCommentInfo(bundle.getString(URL))
            bt_send.setOnClickListener{
                val content = et_comment.text.toString()
                if(UserManager.status) {
                    if (TextUtils.isEmpty(content)) {
                        showToast("请输入评论内容")
                    } else {
                        if(loadingMore) {
                            loadingMore = false
                            presenter.addVideoComment(bundle.getString(URL), content)
                        }
                    }
                }else{
                       showToast("请先登录")
                }
            }
        }

    }

    override fun getCommentSuccess(dataList: ArrayList<CommentBean.Row>) {
        (mRecyclerView.adapter as CommentAdapter).addData(dataList)
        tv_total_comment.text = context?.getString(R.string.total_count,dataList.size)
        showComment()
    }

    override fun getCommentSuccess(row: CommentBean.Row) {
        (mRecyclerView.adapter as CommentAdapter).addData(row)
        loadingMore = true
        tv_total_comment.text = context?.getString(R.string.total_count, mRecyclerView.adapter!!.itemCount)
        et_comment.setText("")
        showComment()
    }

    override fun notGetComment() {
        no_data_view.visibility = ViewGroup.VISIBLE
        mRecyclerView.visibility = ViewGroup.GONE
    }

    fun showComment(){
        no_data_view.visibility = ViewGroup.GONE
        mRecyclerView.visibility = ViewGroup.VISIBLE
    }



    override fun getCommentError() {
        loadingMore = true
        showToast("请联系管理员开启服务器")
        multipleStatusView.showEmpty()
    }

    override fun sendCommentFailure() {
        loadingMore = true
        showToast("发送失败，请联系管理员开启服务器")
    }


    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }
}