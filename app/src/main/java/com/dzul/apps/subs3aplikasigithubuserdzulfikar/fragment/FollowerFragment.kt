package com.dzul.apps.subs3aplikasigithubuserdzulfikar.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.adapter.ListViewFollowerAdapter
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.R
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment() {

    // Instantiate Adapter and View Model
    private lateinit var listViewFollowerAdapter: ListViewFollowerAdapter
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userLogin : String

    companion object {
        const val EXTRA_USERDATA = "extra_userdata"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLoginInfo()
        setRecyclerView()
        setViewModel()
    }

    // Receive intent data from activity
    private fun getLoginInfo(){
        userLogin = activity?.intent?.getStringExtra(EXTRA_USERDATA).orEmpty()
    }

    // Set RecyclerView adapter
    private fun setRecyclerView(){
        listViewFollowerAdapter = ListViewFollowerAdapter()
        listViewFollowerAdapter.notifyDataSetChanged()

        recycleViewFollowers.layoutManager = LinearLayoutManager(activity)
        recycleViewFollowers.adapter = listViewFollowerAdapter
    }

    // Set DetailViewModel
    private fun setViewModel(){
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        detailViewModel.setFollowersData(userLogin)

        detailViewModel.getFollowersData().observe(viewLifecycleOwner, Observer { userItems ->
            listViewFollowerAdapter.setFollowersData(userItems)
        })
    }

}