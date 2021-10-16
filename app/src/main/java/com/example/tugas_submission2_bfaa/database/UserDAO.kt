package com.example.tugas_submission2_bfaa.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Query("SELECT * from user ORDER BY username ASC")
    fun getAllUser(): LiveData<List<User>>

    @Query("DELETE FROM user WHERE username = :userName")
    fun deleteUser( userName :String)
}