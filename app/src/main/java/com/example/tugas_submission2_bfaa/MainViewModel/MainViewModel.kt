package com.example.tugas_submission2_bfaa.MainViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tugas_submission2_bfaa.Datamodel.UserFollowDatamodel

class MainViewModel : ViewModel() {

    private val _userFollowers = MutableLiveData<UserFollowDatamodel>()
    val userFollowers: LiveData<UserFollowDatamodel> = _userFollowers



}