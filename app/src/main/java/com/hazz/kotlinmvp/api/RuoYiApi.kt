package com.hazz.kotlinmvp.api

import com.hazz.kotlinmvp.mvp.model.bean.CommentBean
import com.hazz.kotlinmvp.mvp.model.bean.StatusBean
import retrofit2.Call
import retrofit2.http.*

interface RuoYiApi {

    @POST("/login")
    @FormUrlEncoded
    fun getLoginInfo(@Field("username") username:String,
                     @Field("password") password:String,
                     @Field("rememberMe") rememberMe:String): Call<StatusBean>

    @POST("/register")
    @FormUrlEncoded
    fun getRegisterInfo(@Field("loginName") loginName:String,
                        @Field("password") password:String): Call<StatusBean>

    @GET("/logout")
    fun getLogoutInfo(@Header("Cookie") sessionId:String): Call<Any>

    @POST("/system/comment/list")
    @FormUrlEncoded
    fun getCommentInfo(@Field("mvUrl") url:String) : Call<CommentBean>

    @POST("/system/comment/add")
    @FormUrlEncoded
    fun addCommentInfo(@Header("Cookie") sessionId:String ,@Field("mvUrl") url:String, @Field("userId") id:Int, @Field("content") content:String) : Call<StatusBean>

    @POST("system/user/resetSelfPwd")
    @FormUrlEncoded
    fun getResetInfo(@Header("Cookie") sessionId:String, @Field("loginName") loginName: String, @Field("password") password: String) : Call<StatusBean>
}