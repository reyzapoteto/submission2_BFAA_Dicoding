package com.example.tugas_submission2_bfaa.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_submission2_bfaa.Helper.UserListDiffCallback
import com.example.tugas_submission2_bfaa.Main.DetailActivity
import com.example.tugas_submission2_bfaa.R
import com.example.tugas_submission2_bfaa.database.User
import com.example.tugas_submission2_bfaa.databinding.UserDatamodelBinding

class UserFavoriteAdapter(val activity: AppCompatActivity) :
    RecyclerView.Adapter<UserFavoriteAdapter.UserViewHolder>() {

    private var listUserFavorite = ArrayList<User>()

    fun setListUser(listUser: List<User>) {
        val diffCallBack = UserListDiffCallback(this.listUserFavorite, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        this.listUserFavorite.clear()
        this.listUserFavorite.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }


    inner class UserViewHolder(private val binding: UserDatamodelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                tvIsUser.setText(R.string.user)

                Glide
                    .with(activity)
                    .load(user.avatar_url)
                    .into(imgUserProfileDatamodel)

                tvUserName.text = user.name

                backgroundUser.setOnClickListener {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("name", user.name)
                    intent.putExtra("status","Favorite")
                    activity.startActivity(intent)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            UserDatamodelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUserFavorite[position])
    }

    override fun getItemCount(): Int {
        return listUserFavorite.size
    }
}