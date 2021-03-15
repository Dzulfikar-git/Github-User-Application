package com.dzul.apps.consumerapp


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzul.apps.consumerapp.adapter.ListViewFavoriteAdapter
import com.dzul.apps.consumerapp.helper.MappingHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var favoriteAdapter: ListViewFavoriteAdapter

    companion object {
        val tblFavorites = "FavoriteUsers"
        val PROVIDER_NAME = "com.dzul.apps.subs3aplikasigithubuserdzulfikar.favoriteUsers" // Authority
        val uriString = "content://$PROVIDER_NAME/$tblFavorites"
        val CONTENT_URI = Uri.parse(uriString)
        
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Consumer App"

        // set data to recycler list adapter
        showRecyclerListAdapter()

        //load data from Github User App using Content Provider
        loadDataAsync()

    }

    // onResume do reload data
    override fun onResume() {
        super.onResume()
        loadDataAsync()
    }


    // Set Adapter to RecyclerView
    private fun showRecyclerListAdapter(){
        favoriteAdapter = ListViewFavoriteAdapter()
        favoriteAdapter.notifyDataSetChanged()

        rvFavoriteList.layoutManager = LinearLayoutManager(this)
        rvFavoriteList.adapter = favoriteAdapter
        }

    // Load data from github user app
    private fun loadDataAsync(){
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFav = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorites = deferredFav.await()
            if(favorites.size > 0){
                txt_empty_users.visibility = View.GONE
                rvFavoriteList.visibility = View.VISIBLE
                favoriteAdapter.favUsers = favorites
                Log.d("favorites", favorites.toString())
            }
            else {
                rvFavoriteList.visibility = View.GONE
                txt_empty_users.visibility = View.VISIBLE
                favoriteAdapter.favUsers = ArrayList()
                Log.d("favorites", "favorites empty")
            }
        }
    }

}
