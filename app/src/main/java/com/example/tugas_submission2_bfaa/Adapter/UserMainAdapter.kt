package com.example.tugas_submission2_bfaa.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_submission2_bfaa.Datamodel.UserDatamodel
import com.example.tugas_submission2_bfaa.DetailActivity
import com.example.tugas_submission2_bfaa.R
import de.hdodenhof.circleimageview.CircleImageView

class UserMainAdapter(
    private val activity: Context,
    private var listDatamodel: ArrayList<UserDatamodel>,
) : RecyclerView.Adapter<UserMainAdapter.ViewHolder>(),Filterable {


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
        Glide
            .with(activity)
            .load(listDatamodel[position].avatar_url)
            .into(holder.avatar)
        holder.title.text = listDatamodel[position].login
        holder.isUser.text = listDatamodel[position].type

        holder.backgroundUSer.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("name", listDatamodel[position].login)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listDatamodel.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(p0: CharSequence?): FilterResults {
                val queryString = p0.toString()

                val filterResult = FilterResults()
                filterResult.values =
                    if (queryString.isEmpty()) {
                        listDatamodel
                    } else {
                        listDatamodel.filter {
                            it.login.contains(queryString, ignoreCase = true) || it.login.contains(
                                p0.toString()
                            )
                        }
                    }
                return filterResult
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listDatamodel  = p1!!.values as ArrayList<UserDatamodel>
                notifyDataSetChanged()
            }

        }
    }
}