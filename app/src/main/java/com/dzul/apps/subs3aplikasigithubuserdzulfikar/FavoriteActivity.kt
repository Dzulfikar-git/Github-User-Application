package com.dzul.apps.subs3aplikasigithubuserdzulfikar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.adapter.ListViewFavoriteAdapter
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.entity.FavoriteEntity
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.utility.FavoriteApplication
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.viewmodel.FavoriteViewModel
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.viewmodel.FavoriteViewModelFactory
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteAdapter: ListViewFavoriteAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory((application as FavoriteApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.title = getString(R.string.favorite_title)

        // Set View Model
        setViewModel()

        // Set Adapter
        showRecyclerListAdapter()

    }

    // Set Adapter to RecyclerView
    private fun showRecyclerListAdapter(){
        favoriteAdapter = ListViewFavoriteAdapter()
        favoriteAdapter.notifyDataSetChanged()

        favoriteRecyclerView.layoutManager = LinearLayoutManager(this)
        favoriteRecyclerView.adapter = favoriteAdapter

        favoriteAdapter.setOnItemClickCallback(object : ListViewFavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(favoriteEntity: FavoriteEntity) {
                val detailIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_USERDATA, favoriteEntity.userLogin)
                startActivity(detailIntent)
            }
        })
    }

    // Set ViewModel to use FavoriteViewModel
    private fun setViewModel(){
        // Observe data
        favoriteViewModel.allFavoriteUsers.observe(this, Observer { favEntity ->
            favEntity.let {
                favoriteAdapter.setData(favEntity)
            }
        })
    }
}