package com.dzul.apps.subs3aplikasigithubuserdzulfikar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.BuildConfig
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.data.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class DetailViewModel() : ViewModel() {

    val userData = MutableLiveData<UserItems>()
    val listUserFollowers = MutableLiveData<ArrayList<UserItems>>()
    val listUserFollowings = MutableLiveData<ArrayList<UserItems>>()

    // To Get Data From API and add it to userData
    fun setDetailUserData(userLogin: String){
        val userDetail = UserItems()

        val url = "https://api.github.com/users/${userLogin}"

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    userDetail.login = responseObject.getString("login")
                    userDetail.avatar_url = responseObject.getString("avatar_url")
                    userDetail.name = responseObject.getString("name")
                    userDetail.company = responseObject.getString("company")
                    userDetail.email = responseObject.getString("email")
                    userDetail.location = responseObject.getString("location")
                    userData.postValue(userDetail)
                } catch (e: Exception){
                    Log.d("onSuccessDetailUser:", "Exception : $e")
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                Log.d("onFailureDetailUser:", "Failure : ${error.message}")
            }
        })
    }

    // Return userData as LiveData to DetailActivity
    fun getDetailUserData() : LiveData<UserItems>{
        return userData
    }

    // Get Followers Data from API and Add it to listUserFollowers
    fun setFollowersData(userLogin: String){
        // UserItems object as Array
        val userFollowers = ArrayList<UserItems>()

        val url = "https://api.github.com/users/${userLogin}/followers"


        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)

                    // Loop JSON Objects
                    for (i in 0 until responseObject.length()){
                        val followers = responseObject.getJSONObject(i)
                        val userFollower = UserItems()
                        userFollower.login = followers.getString("login")
                        userFollower.avatar_url = followers.getString("avatar_url")
                        userFollowers.add(userFollower)
                    }
                    listUserFollowers.postValue(userFollowers)
                } catch (e: Exception){
                    Log.d("onSuccessFollowersExc", "Exception : ${e.message}")
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                Log.d("onFailureFollowersData:", "Failure : ${error.message}, $statusCode")
            }
        })
    }

    // Return listUserFollowers as LiveData Array List
    fun getFollowersData() : LiveData<ArrayList<UserItems>>{
        return listUserFollowers
    }

    // To Get Following Data from API and add it to listUserFollowings
    fun setFollowingData(userLogin: String){
        // UserItems object as Array
        val userFollowings = ArrayList<UserItems>()

        val url = "https://api.github.com/users/${userLogin}/following"

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONArray(result)

                    // Loop JSON Objects
                    for (i in 0 until responseObject.length()){
                        val followings = responseObject.getJSONObject(i)
                        val userFollowing = UserItems()
                        userFollowing.login = followings.getString("login")
                        userFollowing.avatar_url = followings.getString("avatar_url")
                        userFollowings.add(userFollowing)
                    }
                    listUserFollowings.postValue(userFollowings)
                } catch (e: Exception){
                    Log.d("onSuccessFollowingExc", "Exception : ${e.message}")
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                Log.d("onFailureFollowingData:", "Failure : ${error.message}")
            }
        })
    }

    // Return listUserFollowings as LiveData
    fun getFollowingsData() : LiveData<ArrayList<UserItems>>{
        return listUserFollowings
    }
}