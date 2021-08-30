package com.example.tugas_submission2_bfaa.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_submission2_bfaa.Datamodel.UserFollowingDatamodel
import com.example.tugas_submission2_bfaa.R
import de.hdodenhof.circleimageview.CircleImageView

class FollowingAdapter(
    private val activity: Context,
    private val listFollowingDatamodel: ArrayList<UserFollowingDatamodel>
) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val avatar: CircleImageView = itemView.findViewById(R.id.imgUserProfileDatamodel)
        val title: TextView = itemView.findViewById(R.id.tvUserName)
        val isUser: TextView = itemView.findViewById(R.id.tvIsUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_datamodel, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide
            .with(activity)
            .load(listFollowingDatamodel[position].avatar_url)
            .into(holder.avatar)

        holder.isUser.text = listFollowingDatamodel[position].type
        holder.title.text = listFollowingDatamodel[position].login
    }

    override fun getItemCount(): Int {
        return listFollowingDatamodel.size
    }
}