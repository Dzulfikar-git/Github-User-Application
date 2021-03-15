package com.dzul.apps.subs3aplikasigithubuserdzulfikar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.adapter.DetailActivityPagerAdapter
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.data.UserItems
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.database.entity.FavoriteEntity
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.utility.FavoriteApplication
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.viewmodel.DetailViewModel
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.viewmodel.FavoriteViewModel
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.viewmodel.FavoriteViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel : DetailViewModel
    private lateinit var users : UserItems
    private lateinit var userLogin : String

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory((application as FavoriteApplication).repository)
    }

    companion object {
        const val EXTRA_USERDATA = "extra_userdata"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Set return activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getUserLoginInfo()
        getFavoriteInfo()
        setPageAdapter()
        setViewModel()

        detail_item_notfavbtn.setOnClickListener {
            addFavorite()
            setFavoriteButton(true)
            Toast.makeText(this, getString(R.string.detail_favorite_yes), Toast.LENGTH_SHORT).show()
        }

        detail_item_yesfavbtn.setOnClickListener {
            deleteFavorite()
            setFavoriteButton(false)
            Toast.makeText(this, getString(R.string.detail_favorite_no), Toast.LENGTH_SHORT).show()
        }
    }

    // Set TabLayout Pager Adapter
    private fun setPageAdapter(){
        val detailActivityPagerAdapter = DetailActivityPagerAdapter(this, supportFragmentManager)
        viewPagerDetail.adapter = detailActivityPagerAdapter
        tabsDetail.setupWithViewPager(viewPagerDetail)

        supportActionBar?.elevation = 0f
    }

    // Get userlogin data from previous intent
    private fun getUserLoginInfo(){
        userLogin = intent?.getStringExtra(EXTRA_USERDATA).orEmpty()
        supportActionBar?.title = userLogin
        Log.d("DetailActivity", "userLogin : $userLogin")
    }

    // Set DetailViewModel to Activity and Observer
    private fun setViewModel(){
        // Set View Model
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        // Instantiate UserItems and retreive data from API
        detailViewModel.setDetailUserData(userLogin)

        // Set items value and conditional value if null
        detailViewModel.getDetailUserData().observe(this, Observer{userItems ->
            detail_item_username.text = userItems.login
            if(userItems.name != "null")
                detail_item_name.text = userItems.name
            else
                detail_item_name.text = getString(R.string.detail_item_nullname)


            if (userItems.company != "null")
                detail_item_usercompany.text = userItems.company
            else
                detail_item_usercompany.text = getString(R.string.detail_name_nullcompany)

            if(userItems.email != "null"){
                detail_item_useremail.text = userItems.email
            } else {
                detail_item_useremail.text = getString(R.string.detail_name_nullemail)
            }

            if(userItems.location != "null") {
                detail_item_userlocation.text = userItems.location
            }
            else {
                detail_item_userlocation.text = getString(R.string.detail_name_nulllocation)
            }
            Glide.with(applicationContext)
                .load(userItems.avatar_url)
                .fitCenter()
                .into(detail_item_img)

            users = userItems
            Log.d("DetailViewModel", userLogin)
        })
    }

    // Add user to Favorite and save to Room Database
    fun addFavorite(){
        GlobalScope.launch {
            favoriteViewModel.insert(FavoriteEntity(userLogin = userLogin, userImage = users.avatar_url))
            Log.d("Database", favoriteViewModel.allFavoriteUsers.toString())
        }

    }

    // Add user from Favorite and remove from Room Database
    fun deleteFavorite(){
        GlobalScope.launch {
            favoriteViewModel.deleteUser(userLogin)
            Log.d("deleteFavorite", "Deleted Favorite $userLogin")
        }
    }

    // Check if user is already favorite
    fun getFavoriteInfo(){
        GlobalScope.launch(Dispatchers.Default) {
            if (favoriteViewModel.getUser(userLogin).size > 0){
                setFavoriteButton(true)
            }
            else {
                setFavoriteButton(false)
            }
        }
    }

    // Set Favorite Button depends on user is favorite or not
    fun setFavoriteButton(fav: Boolean){
        if(fav == true){
            detail_item_yesfavbtn.visibility = View.VISIBLE
            detail_item_notfavbtn.visibility = View.GONE
            Log.d("isFavorite", "True")
        } else {
            detail_item_yesfavbtn.visibility = View.GONE
            detail_item_notfavbtn.visibility = View.VISIBLE
            Log.d("isFavorite", "False")
        }
    }
}