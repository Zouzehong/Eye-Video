package com.hazz.kotlinmvp.mvp.presenter

import com.hazz.kotlinmvp.UserManager
import com.hazz.kotlinmvp.base.BasePresenter
import com.hazz.kotlinmvp.mvp.contract.CommentContract
import com.hazz.kotlinmvp.mvp.model.CommentModel
import com.hazz.kotlinmvp.mvp.model.bean.CommentBean
import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentPresenter:CommentContract.Presenter,BasePresenter<CommentContract.View>(){
    private val commentModel by lazy {
        CommentModel()
    }
    override fun getCommentInfo(url: String) {
        mRootView?.showLoading()
        commentModel.queryCommentInfo(url).enqueue(
                object : Callback<CommentBean> {
                    override fun onResponse(call: Call<CommentBean>, response: Response<CommentBean>) {
                        mRootView?.dismissLoading()
                        val body = response.body()
                        if (body == null) {
                            mRootView?.getCommentError()
                        }else {
                            if (body.code == 0) {
                                if (body.total > 0) {
                                    mRootView?.getCommentSuccess(ArrayList(body.rows))
                                } else {
                                    mRootView?.notGetComment()
                                }
                            }
                        }
                    }
                    override fun onFailure(p0: Call<CommentBean>, p1: Throwable) {

                    }

                }
        )
    }

    override fun addVideoComment(url: String, content: String) {
        commentModel.addCommentInfo(url,UserManager.username.toInt(),content,UserManager.sessionId).enqueue(
                object : Callback<StatusBean>{
                    override fun onResponse(call: Call<StatusBean>, response: Response<StatusBean>) {
                        val body = response.body()
                        if (body == null) {
                            mRootView?.sendCommentFailure()
                        }else{
                        if (body.code == 0){
                            mRootView?.getCommentSuccess(CommentBean.Row("",
                                    "","","","","",CommentBean.Params(""),0,url,UserManager.username.toInt(),content))
                        }
                    }}

                    override fun onFailure(p0: Call<StatusBean>, p1: Throwable) {

                    }

                }
        )
    }
}