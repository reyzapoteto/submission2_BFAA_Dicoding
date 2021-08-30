package com.example.tugas_submission2_bfaa.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_submission2_bfaa.Datamodel.UserFollowDatamodel
import com.example.tugas_submission2_bfaa.R
import de.hdodenhof.circleimageview.CircleImageView

class FollowAdapter : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    private var listFollowDatamodel = arrayListOf<UserFollowDatamodel>()

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
        val activity = holder.itemView.context
        Glide
            .with(activity)
            .load(listFollowDatamodel[position].avatar_url)
            .into(holder.avatar)

        holder.isUser.text = listFollowDatamodel[position].type
        holder.title.text = listFollowDatamodel[position].login
    }

    fun subList(list: ArrayList<UserFollowDatamodel>) {
        listFollowDatamodel = list
    }

    override fun getItemCount(): Int {
        return listFollowDatamodel.size
    }
}