package com.hazz.kotlinmvp.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter
import com.hazz.kotlinmvp.mvp.model.bean.CommentBean

interface CommentContract {
    interface View : IBaseView {
        fun getCommentSuccess(dataList: ArrayList<CommentBean.Row>)
        fun notGetComment()
        fun getCommentError()
        fun getCommentSuccess(row: CommentBean.Row)
        fun sendCommentFailure()
    }

    interface Presenter : IPresenter<View> {
        fun getCommentInfo(url: String)
        fun addVideoComment(url: String, content: String)
    }
}