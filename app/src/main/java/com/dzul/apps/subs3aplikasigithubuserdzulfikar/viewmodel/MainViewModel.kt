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
import org.json.JSONObject

class MainViewModel: ViewModel() {
    val listUsers = MutableLiveData<ArrayList<UserItems>>()

    // Request data from API
    fun setUser(username: String){
        val listUserItems = ArrayList<UserItems>()

        val url = "https://api.github.com/search/users?q=${username}"

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object: AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try{
                    // Parsing JSON
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    // loop JSON Array data into UserItems
                    for(i in 0 until list.length()){
                        val users = list.getJSONObject(i)
                        val userItems = UserItems()
                        userItems.login = users.getString("login")
                        userItems.avatar_url = users.getString("avatar_url")
                        listUserItems.add(userItems)
                    }
                    listUsers.postValue(listUserItems)
                } catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable) {
                Log.d("Exception", error.message.toString())
            }
        })

    }

    // Return Users Array data to MainActivity
    fun getUsers(): LiveData<ArrayList<UserItems>>{
        return listUsers
    }
}