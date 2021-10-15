package com.example.tugas_submission2_bfaa.Adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_submission2_bfaa.Datamodel.UserDatamodel
import com.example.tugas_submission2_bfaa.Main.DetailActivity
import com.example.tugas_submission2_bfaa.R
import de.hdodenhof.circleimageview.CircleImageView

class UserMainAdapter : RecyclerView.Adapter<UserMainAdapter.ViewHolder>() {

    private var listDatamodel = arrayListOf<UserDatamodel>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: CircleImageView = itemView.findViewById(R.id.imgUserProfileDatamodel)
        val title: TextView = itemView.findViewById(R.id.tvUserName)
        val backgroundUSer: ConstraintLayout = itemView.findViewById(R.id.backgroundUser)
        val isUser: TextView = itemView.findViewById(R.id.tvIsUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_datamodel, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        Glide
            .with(context)
            .load(listDatamodel[position].avatar_url)
            .into(holder.avatar)

        holder.title.text = listDatamodel[position].login
        holder.isUser.text = listDatamodel[position].type

        holder.backgroundUSer.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("name", listDatamodel[position].login)
            intent.putExtra("status","detail")
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listDatamodel.size
    }

    fun submitList(list: ArrayList<UserDatamodel>) {
        listDatamodel = list
    }

}