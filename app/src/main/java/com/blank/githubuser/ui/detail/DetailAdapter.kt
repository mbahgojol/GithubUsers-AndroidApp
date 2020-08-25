package com.blank.githubuser.ui.detail

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.blank.githubuser.R
import com.blank.githubuser.ui.detail.followers.FollowersFragment
import com.blank.githubuser.ui.detail.following.FollowingFragment

class DetailAdapter(
    private val context: Context,
    private val username: String,
    fm: FragmentManager
) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.followers, R.string.followings)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = TAB_TITLES.size
}