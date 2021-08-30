package com.example.tugas_submission2_bfaa.API

import com.example.tugas_submission2_bfaa.Datamodel.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiUser {


    @GET("users")
    fun getAllUsers(): Call<ArrayList<UserDatamodel>>


    @GET("users/{name}")
    fun getUsersDetail(
        @Path("name") namuUser: String
    ): Call<UserDetailDatamodel>

    @GET("search/users")
    fun getUserSelected(
        @Query("q") username: String
    ): Call<UserSearchDatamodel>


    @GET("users/{name}/following")
    fun getUserFollowing(
        @Path("name") nameUser: String
    ): Call<ArrayList<UserFollowDatamodel>>

    @GET("users/{name}/followers")
    fun getUserFollowers(
        @Path("name") nameUser: String
    ): Call<ArrayList<UserFollowDatamodel>>

}
