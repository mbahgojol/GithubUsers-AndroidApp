package com.blank.githubuser.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.blank.githubuser.R
import com.blank.githubuser.data.model.User
import com.blank.githubuser.ui.base.BaseFragment
import com.blank.githubuser.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : BaseFragment() {
    private val viewModel by viewModels<DetailViewModel>()
    override fun layoutId() = R.layout.fragment_detail
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args by navArgs<DetailFragmentArgs>()
        username = args.username.toString()

        val sectionsPagerAdapter = DetailAdapter(requireContext(), username, childFragmentManager)
        viewpager.adapter = sectionsPagerAdapter
        tabLayout.setupWithViewPager(viewpager)

        observe(viewModel.mutableResultState, this::manageResultStateUser)
        viewModel.fetchUsers(username)
        viewpager.addOnPageChangeListener(parentHeader)
    }

    private fun manageResultStateUser(state: ResultState) {
        when (state) {
            is ResultState.Success<*> -> {
                val data = state.data as User
                ivPhoto.setImages(data.avatarUrl ?: ANONYM_PERSON_ICON)
                tvTitle.text = data.name
                tvLocation.text = data.location ?: requireContext().noLocation()
                tvCountFollowers.text = (data.followers ?: 0).toString()
                tvCountFollowings.text = (data.following ?: 0).toString()
                tvCountRepository.text = (data.publicRepos ?: 0).toString()
            }

            is ResultState.Loading -> {
                println("Loading = ${state.isloading}")
                pbDetail.isVisible = state.isloading
            }

            is ResultState.Error -> {
                if (state.e.message == ERROR_NOCONNECTION) {
                    showMsgError(ERROR_NOCONNECTION)
                } else {
                    showMsgError(state.e.message.toString())
                }
            }
        }
    }

    private fun showMsgError(vararg s: String) {
        Snackbar
            .make(parentMotion, s[0], Snackbar.LENGTH_INDEFINITE)
            .setAction(requireContext().resources.getString(R.string.retry)) {
                viewModel.fetchUsers(username)
            }.show()
    }
}