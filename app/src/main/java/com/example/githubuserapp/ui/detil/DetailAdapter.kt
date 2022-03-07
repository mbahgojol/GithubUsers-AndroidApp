package com.example.githubuserapp.ui.detil

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailAdapter(fa: FragmentActivity, private val fragments: MutableList<Fragment>) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}