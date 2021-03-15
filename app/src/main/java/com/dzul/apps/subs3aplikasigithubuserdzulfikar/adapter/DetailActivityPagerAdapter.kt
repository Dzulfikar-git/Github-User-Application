package com.dzul.apps.subs3aplikasigithubuserdzulfikar.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.fragment.FollowerFragment
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.fragment.FollowingFragment
import com.dzul.apps.subs3aplikasigithubuserdzulfikar.R

class DetailActivityPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    // TabTitles into intArray
    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.follower_title, R.string.following_title)

    // Get fragment count
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }

    // Return Tab Count
    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    // Set Page Title for each fragment
    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }
}