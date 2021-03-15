package com.dzul.apps.subs3aplikasigithubuserdzulfikar.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.R
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.data.UserItems
import kotlinx.android.synthetic.main.rv_githubusers_list.view.*

class ListViewUserAdapter: RecyclerView.Adapter<ListViewUserAdapter.UserViewHolder>() {
    // Instantiate UserItems Object
    private val userData = ArrayList<UserItems>()
    private var onItemClickCallback: OnItemClickCallback? = null

    // Set Data to Recycler View
    fun setData(items: ArrayList<UserItems>){
        userData.clear()
        userData.addAll(items)
        notifyDataSetChanged()
    }

    // Clear data in Recycler View
    fun clearData(){
        userData.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_githubusers_list, viewGroup, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        userViewHolder.bind(userData[position])
    }

    override fun getItemCount(): Int {
        return userData.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    // Create ViewHolder Class
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Bind data to RecyclerView Items
        fun bind(userItems: UserItems){
            with(itemView){
                rv_githubusers_list_txtusername.text = userItems.login

                // Using Glide to Add Image from URL
                Glide.with(context)
                    .load(userItems.avatar_url)
                    .centerCrop()
                    .placeholder(Color.GRAY)
                    .into(rv_githubusers_list_imguser)

                itemView.setOnClickListener{onItemClickCallback?.onItemClicked(userItems)}
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(userItems: UserItems)
    }

}