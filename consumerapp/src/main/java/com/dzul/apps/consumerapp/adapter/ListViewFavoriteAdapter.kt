package com.dzul.apps.consumerapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dzul.apps.consumerapp.R
import com.dzul.apps.consumerapp.data.FavoriteEntity
import kotlinx.android.synthetic.main.rv_favorite_list.view.*

class ListViewFavoriteAdapter : RecyclerView.Adapter<ListViewFavoriteAdapter.FavoriteViewHolder>() {

    var favUsers  = ArrayList<FavoriteEntity>()
        set(favUsers){
            this.favUsers.clear()
            this.favUsers.addAll(favUsers)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.rv_favorite_list, viewGroup, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(favoriteViewHolder: FavoriteViewHolder, position: Int) {
        favoriteViewHolder.bind(favUsers[position])
    }

    override fun getItemCount(): Int {
        return favUsers.size
    }


    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(favoriteEntity: FavoriteEntity){
            with(itemView){
                rv_favorite_list_txtusername.text = favoriteEntity.userLogin

                Glide.with(context)
                    .load(favoriteEntity.userImage)
                    .centerCrop()
                    .placeholder(android.R.color.darker_gray)
                    .into(rv_favorite_list_image)

            }

        }
    }

}