package com.example.tugas_submission2_bfaa.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * from user ORDER BY username ASC")
    fun getAllUser(): LiveData<List<User>>

}