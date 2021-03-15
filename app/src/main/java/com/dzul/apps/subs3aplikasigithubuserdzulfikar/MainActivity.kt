package com.dzul.apps.subs3aplikasigithubuserdzulfikar

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.adapter.ListViewUserAdapter
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.data.PreferencesItem
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.data.UserItems
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.preference.AppPreferences
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter : ListViewUserAdapter
    private lateinit var mainViewModel : MainViewModel
    private lateinit var appPreferences: AppPreferences
    private lateinit var prefsItems: PreferencesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // List Adapter
        showRecyclerListAdapter()

        // Set MainViewModel to Activity
        setViewModel()

        actionBar?.setDisplayHomeAsUpEnabled(true)

        // Get Shared Preferences
        appPreferences = AppPreferences(this)

    }

    private fun showRecyclerListAdapter(){
        // Set Recycler view Adapter
        userAdapter = ListViewUserAdapter()
        userAdapter.notifyDataSetChanged()

        recycleViewUsers.layoutManager = LinearLayoutManager(this)
        recycleViewUsers.adapter = userAdapter

        userAdapter.setOnItemClickCallback(object: ListViewUserAdapter.OnItemClickCallback{
            override fun onItemClicked(userItems: UserItems) {
                // Intent to DetailActivity while passing login data from userItems
                val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_USERDATA, userItems.login.toString())
                startActivity(detailIntent)
            }
        })
    }

    // Set MainViewModel to Activity and Observer
    private fun setViewModel(){
        // Set View Model
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        /// Update data in RecyclerView using Observer
        mainViewModel.getUsers().observe(this, Observer { userItems ->
            if(userItems.isEmpty()){
                tv_searchResult.visibility = View.VISIBLE
                showLoading(false)
            } else {
                tv_searchResult.visibility = View.GONE
                userAdapter.setData(userItems)
                showLoading(false)
            }
        })
    }


    // Search View Function
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate Menu Layout
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        // Search Manager
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.option_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.option_searchhint)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {

                // Send search data to  ViewModel
                tv_searchResult.visibility = View.GONE
                showLoading(true)
                userAdapter.clearData()
                mainViewModel.setUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    // Change Language and Settings Option Selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.option_changeLocale -> {
                val localeIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(localeIntent)
            }

            R.id.option_setting -> {
                val settingIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                prefsItems = appPreferences.getPreferences()
                settingIntent.putExtra("PREFS", prefsItems)
                startActivity(settingIntent)
            }

            R.id.option_favorite -> {
                val favoriteIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(favoriteIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // Progress Bar Shown
    private fun showLoading(state: Boolean){
        if(state){
            searchProgressBar.visibility = View.VISIBLE
        } else {
            searchProgressBar.visibility = View.GONE
        }
    }


}