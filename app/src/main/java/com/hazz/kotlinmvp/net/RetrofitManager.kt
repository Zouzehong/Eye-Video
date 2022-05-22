package com.hazz.kotlinmvp.net

import android.util.Log
import com.bumptech.glide.RequestBuilder
import com.hazz.kotlinmvp.MyApplication
import com.hazz.kotlinmvp.UserManager
import com.hazz.kotlinmvp.api.ApiService
import com.hazz.kotlinmvp.api.RuoYiApi
import com.hazz.kotlinmvp.api.UrlConstant
import com.hazz.kotlinmvp.utils.AppUtils
import com.hazz.kotlinmvp.utils.NetworkUtil
import com.hazz.kotlinmvp.utils.Preference
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by xuhao on 2017/11/16.
 *
 */

object RetrofitManager{

    val service: ApiService by lazy (LazyThreadSafetyMode.SYNCHRONIZED){
        getRetrofit().create(ApiService::class.java)
    }

    val ruoyi : RuoYiApi by lazy (LazyThreadSafetyMode.SYNCHRONIZED){
        getRetrofit2().create(RuoYiApi::class.java)
    }

    private var token:String by Preference("token","")

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    .addQueryParameter("udid", "d2807c895f0348a180148c9dfa6f2feeac0781b5")
                    .addQueryParameter("deviceModel", AppUtils.getMobileModel())
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    .header("token", token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * 设置缓存
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            if (NetworkUtil.isNetworkAvailable(MyApplication.context)) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("nyn")
                        .build()
            }
            response
        }
    }

    private fun getRetrofit(): Retrofit {
        // 获取retrofit的实例
        return Retrofit.Builder()
                .baseUrl(UrlConstant.BASE_URL)  //自己配置
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

    private fun getRetrofit2() : Retrofit{
        return Retrofit.Builder()
                .baseUrl(UrlConstant.ROOT_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        //添加一个log拦截器,打印所有的log
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        //设置 请求的缓存的大小跟位置
        val cacheFile = File(MyApplication.context.cacheDir, "cache")
        val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

        return OkHttpClient.Builder()
                .addInterceptor(addQueryParameterInterceptor())  //参数添加
                .addInterceptor(addHeaderInterceptor()) // token过滤
//              .addInterceptor(addCacheInterceptor())
                .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                .cache(cache)  //添加缓存
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .build()
    }

    private fun sessionInterceptor():Interceptor{
        return Interceptor { chain ->
            val originalRequest = chain.request()
            var originalResponse = chain.proceed(originalRequest)
            Log.d("Retrofit","origin " + originalRequest.headers().toString() + originalRequest.body().toString())
            if(originalResponse.code() == 302){
                   originalResponse.body()?.close()
                   val response = chain.proceed(getLoginRequest())
                   if(response.isSuccessful){
                       val cookies = response.headers().values("Set-Cookie")
                       val session = cookies[0]
                       UserManager.sessionId = session.substring(0,session.indexOf(";"))
                       UserManager.saveUserInfo()
                   }
                   val newRequest = originalRequest.newBuilder().header("Cookie",UserManager.sessionId).build()
                   originalResponse = chain.proceed(newRequest)
            }
              originalResponse
        }
    }

    private fun getLoginRequest() : Request{
        return Request.Builder()
                .url(UrlConstant.ROOT_URL + "/login")
                .post(FormBody.Builder()
                        .add("username",UserManager.username)
                        .add("password",UserManager.password)
                        .add("rememberMe","false")
                        .build())
                .build()
    }

    private fun getClient(): OkHttpClient{
        return OkHttpClient.Builder()
                .addInterceptor(sessionInterceptor())
                .followRedirects(false)
                .build()
    }
}
