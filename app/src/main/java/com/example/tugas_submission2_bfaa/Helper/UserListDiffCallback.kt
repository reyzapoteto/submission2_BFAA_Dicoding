package com.example.tugas_submission2_bfaa.Helper

import androidx.recyclerview.widget.DiffUtil
import com.example.tugas_submission2_bfaa.database.User

class UserListDiffCallback(
    private val mOldUserList: List<User>,
    private val mNewUserList: List<User>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return mOldUserList.size
    }

    override fun getNewListSize(): Int {
        return mNewUserList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldUserList[oldItemPosition].name == mNewUserList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUserlist = mOldUserList[oldItemPosition]
        val newUserlist = mNewUserList[newItemPosition]
        return oldUserlist.name == newUserlist.name
    }


}