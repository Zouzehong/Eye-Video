package com.hazz.kotlinmvp.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.CommentBean
import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import com.hazz.kotlinmvp.net.RetrofitManager
import retrofit2.Call
import retrofit2.http.Field

class CommentModel {
    fun queryCommentInfo(url: String): Call<CommentBean> {
        return RetrofitManager.ruoyi.getCommentInfo(url)
    }

    fun addCommentInfo(url: String, userId: Int, content: String,sessionId:String): Call<StatusBean> {
        return RetrofitManager.ruoyi.addCommentInfo(sessionId,url,userId,content)
    }
}