package com.example.githubuserapp.ui.detil

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.transform.CircleCropTransformation
import com.example.githubuserapp.R
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.ui.detil.follow.FollowsFragment
import com.example.githubuserapp.ui.setting.SettingActivity
import com.example.githubuserapp.utils.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getParcelableExtra<User>(KEY_USER)

        binding.ivFoto.loadImg(user?.avatar_url) {
            transformations(CircleCropTransformation())
        }

        binding.btnFavorite.setOnClickListener {
            viewModel.setFavorite(user)
        }

        viewModel.resultDeleteFavorite.observe(this) {
            if (it) {
                binding.btnFavorite.changeIconColor(R.color.white)
            } else {
                binding.root.showSnakBar(getString(R.string.gagalDelete))
            }
        }

        viewModel.resultAddFavorite.observe(this) {
            if (it) {
                binding.btnFavorite.changeIconColor(R.color.red)
            } else {
                binding.root.showSnakBar(getString(R.string.gagalFavorite))
            }
        }

        viewModel.findById(user?.id) {
            if (it) {
                binding.btnFavorite.changeIconColor(R.color.red)
            } else {
                binding.btnFavorite.changeIconColor(R.color.white)
            }
        }

        binding.tvFollowers.text =
            text(user?.followers)
                .space()
                .append(getString(R.string.followers))
        binding.tvFollowing.text = text(user?.following)
            .space()
            .append(getString(R.string.following))
        binding.tvRepositori.text = text(user?.public_repos)
            .space()
            .append(getString(R.string.repository))
        binding.tvTitle.text = user?.name
        binding.tvLocation.text = user?.location ?: NoLocation
        binding.tvCompany.text = user?.company ?: NoCompany
        binding.tvUsername.text = text("@").append(user?.login)

        val fragments = mutableListOf<Fragment>(
            FollowsFragment.newInstances(FollowsFragment.FOLLOWING),
            FollowsFragment.newInstances(FollowsFragment.FOLLOWERS)
        )
        val fragmentsTitle =
            mutableListOf(getString(R.string.following), getString(R.string.followers))
        val adapter = DetailAdapter(this, fragments)

        binding.pager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = fragmentsTitle[position]
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == FollowsFragment.FOLLOWERS) {
                    viewModel.getFollowers(user?.login.toString())
                } else {
                    viewModel.getFollowing(user?.login.toString())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewModel.getFollowing(user?.login.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.setting -> {
                Intent(this, SettingActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}