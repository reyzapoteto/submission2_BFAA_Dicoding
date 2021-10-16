package com.example.tugas_submission2_bfaa.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {

    private val mUserDao: UserDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllFavoriteUsers(): LiveData<List<User>> = mUserDao.getAllUser()

    fun insert(user: User) {
        executorService.execute { mUserDao.insert(user) }
    }

    fun deleteUser(username :String){
        executorService.execute { mUserDao.deleteUser(username) }
    }

}