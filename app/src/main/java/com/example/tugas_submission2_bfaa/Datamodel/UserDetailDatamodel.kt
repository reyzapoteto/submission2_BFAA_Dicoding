package com.example.tugas_submission2_bfaa.Datamodel

import com.google.gson.annotations.SerializedName

data class UserDetailDatamodel(

    @SerializedName("login")
    val name: String?,

    @SerializedName("name")
    val fullname: String?,
    val avatar_url: String,
    val company: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int,
    val location: String?


)