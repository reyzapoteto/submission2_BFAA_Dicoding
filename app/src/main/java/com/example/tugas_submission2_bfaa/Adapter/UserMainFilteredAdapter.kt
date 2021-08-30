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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_submission2_bfaa.Datamodel.UserDatamodel
import com.example.tugas_submission2_bfaa.Datamodel.UserSearchDatamodel
import com.example.tugas_submission2_bfaa.DetailActivity
import com.example.tugas_submission2_bfaa.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList

class UserMainFilteredAdapter(
    private val activity: Context,
    var listDatamodel: UserSearchDatamodel
) : RecyclerView.Adapter<UserMainFilteredAdapter.ViewHoldernya>(), Filterable {


    inner class ViewHoldernya(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: CircleImageView = itemView.findViewById(R.id.imgUserProfileDatamodel)
        val title: TextView = itemView.findViewById(R.id.tvUserName)
        val backgroundUSer: ConstraintLayout = itemView.findViewById(R.id.backgroundUser)
        val isUser: TextView = itemView.findViewById(R.id.tvIsUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoldernya {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_datamodel, parent, false)
        return ViewHoldernya(layout)
    }

    override fun getItemCount(): Int {
        return listDatamodel.items.size
    }

    override fun onBindViewHolder(holder: ViewHoldernya, position: Int) {

        Glide
            .with(activity)
            .load(listDatamodel.items[position].avatar_url)
            .into(holder.avatar)

        holder.title.text = listDatamodel.items[position].login
        holder.isUser.text = listDatamodel.items[position].type

        holder.backgroundUSer.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("name", listDatamodel.items[position].login)
            activity.startActivity(intent)
        }

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
                        listDatamodel.items.filter {
                            it.login.contains(queryString, ignoreCase = true) || it.login.contains(
                                p0.toString()
                            )
                        }
                    }
                return filterResult
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listDatamodel = p1!!.values as UserSearchDatamodel
                notifyDataSetChanged()
            }

        }
    }
}