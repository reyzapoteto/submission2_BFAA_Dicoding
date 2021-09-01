package com.example.tugas_submission2_bfaa.Retrofit

import com.example.tugas_submission2_bfaa.API.ApiUser
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUser {

    private const val BASEUSRL = "https://api.github.com/"

    val instance: ApiUser by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASEUSRL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiUser::class.java)
    }
}