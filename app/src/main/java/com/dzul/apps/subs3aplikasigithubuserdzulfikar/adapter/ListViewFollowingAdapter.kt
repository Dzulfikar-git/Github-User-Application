package com.dzul.apps.subs3aplikasigithubuserdzulfikar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.data.UserItems
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.R
import kotlinx.android.synthetic.main.rv_following_list.view.*

class ListViewFollowingAdapter : RecyclerView.Adapter<ListViewFollowingAdapter.FollowingViewHolder>() {

    private val userFollowings = ArrayList<UserItems>()

    fun setFollowingsData(items: ArrayList<UserItems>){
        userFollowings.clear()
        userFollowings.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FollowingViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_following_list, viewGroup, false)
        return FollowingViewHolder(view)
    }

    override fun onBindViewHolder(followingViewHolder: FollowingViewHolder, position: Int) {
        followingViewHolder.bind(userFollowings[position])
    }

    override fun getItemCount(): Int {
        return userFollowings.size
    }

    inner class FollowingViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        // Bind layout items to userItems
        fun bind(userItems: UserItems) {
            with(itemView){
                Glide.with(context)
                    .load(userItems.avatar_url)
                    .centerCrop()
                    .into(rv_following_list_imguser)

                rv_following_list_txtusername.text = userItems.login
            }
        }
    }

}