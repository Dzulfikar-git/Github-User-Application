package com.dzul.apps.subs3aplikasigithubuserdzulfikar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.data.UserItems
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.R
import kotlinx.android.synthetic.main.rv_followers_list.view.*

class ListViewFollowerAdapter : RecyclerView.Adapter<ListViewFollowerAdapter.FollowerViewHolder>() {
    private val userFollowers = ArrayList<UserItems>()

    // Set Data to Recycler View
    fun setFollowersData(items: ArrayList<UserItems>){
        userFollowers.clear()
        userFollowers.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowerViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_followers_list, viewGroup, false )
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(followerViewHolder: FollowerViewHolder, position: Int) {
        followerViewHolder.bind(userFollowers[position])
    }

    override fun getItemCount(): Int {
        return userFollowers.size
    }

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Bind data to Items
        fun bind(userItems: UserItems) {
            with(itemView) {
                Glide.with(context)
                    .load(userItems.avatar_url)
                    .centerCrop()
                    .into(rv_followers_list_imguser)

                rv_followers_list_txtusername.text = userItems.login
            }
        }

    }
}