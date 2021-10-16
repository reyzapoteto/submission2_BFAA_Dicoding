package com.example.tugas_submission2_bfaa.MainViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tugas_submission2_bfaa.database.User
import com.example.tugas_submission2_bfaa.database.UserRepository

class MainViewModel(application: Application) : ViewModel() {

    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<User>> = mUserRepository.getAllFavoriteUsers()

    fun insert(user: User) {
        mUserRepository.insert(user)
    }

    fun deleteUser(username:String){
        mUserRepository.deleteUser(username)
    }

}