package com.example.tugas_submission2_bfaa.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity

@Parcelize
data class User(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var name: String ="",

    @ColumnInfo(name = "fullname")
    var fullname: String? = null,

    @ColumnInfo(name = "avatar")
    var avatar_url: String? = null,

    @ColumnInfo(name = "company")
    var company: String? = null,

    @ColumnInfo(name = "repo")
    var public_repos: String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null

) : Parcelable