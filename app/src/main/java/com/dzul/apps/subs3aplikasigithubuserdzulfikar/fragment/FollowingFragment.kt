package com.dzul.apps.subs3aplikasigithubuserdzulfikar.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.adapter.ListViewFollowingAdapter
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.R
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_following.*


class FollowingFragment : Fragment() {

    // Instantiate ViewModel and Adapter
    private lateinit var listViewFollowingAdapter: ListViewFollowingAdapter
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userLogin: String

    companion object {
        const val EXTRA_USERDATA = "extra_userdata"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLoginInfo()
        setRecyclerView()
        setViewModel()
    }

    // Get Login data from intent activity
    private fun getLoginInfo(){
        userLogin = requireActivity().intent.getStringExtra(EXTRA_USERDATA).toString()
    }

    // Set RecyclerView adapter
    private fun setRecyclerView(){
        listViewFollowingAdapter = ListViewFollowingAdapter()
        listViewFollowingAdapter.notifyDataSetChanged()

        recycleViewFollowing.layoutManager = LinearLayoutManager(activity)
        recycleViewFollowing.adapter = listViewFollowingAdapter
    }

    // Set DetailViewModel
    private fun setViewModel(){
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detailViewModel.setFollowingData(userLogin)

        detailViewModel.getFollowingsData().observe(viewLifecycleOwner, Observer { userItems ->
            listViewFollowingAdapter.setFollowingsData(userItems)
        })
    }

}