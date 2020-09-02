package com.blank.githubuser.ui.detail.following

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.blank.githubuser.R
import com.blank.githubuser.ui.base.BaseFragment
import com.blank.githubuser.ui.detail.following.adapter.FollowingAdapter
import com.blank.githubuser.ui.detail.following.adapter.FollowingLoadStateAdapter
import com.blank.githubuser.utils.ERROR_NOCONNECTION
import com.blank.githubuser.utils.hide
import com.blank.githubuser.utils.observe
import com.blank.githubuser.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.android.synthetic.main.no_internet.*
import kotlinx.android.synthetic.main.no_internet.view.*
import javax.inject.Inject

private const val ARG_USERNAME = "USERNAME"

@AndroidEntryPoint
class FollowingFragment : BaseFragment() {
    private var username: String? = null
    override fun layoutId(): Int = R.layout.fragment_following
    private val viewModel by viewModels<FollowingViewModel>()

    @Inject
    lateinit var adapter: FollowingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.addLoadStateListener(this::manageFollowingState)
        rvFollowing.adapter = adapter.withLoadStateHeaderAndFooter(
            footer = FollowingLoadStateAdapter(adapter::retry),
            header = FollowingLoadStateAdapter(adapter::retry)
        )
        observe(viewModel.followingResultState) {
            adapter.submitData(lifecycle, it)
        }
        username?.let {
            viewModel.getFollowingUsers(it)
        }

        btnRetry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun manageFollowingState(loadState: CombinedLoadStates) {
        val isLoading = loadState.source.refresh is LoadState.Loading
        pbFollowing?.isVisible = isLoading

        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
            ?: loadState.source.refresh as? LoadState.Error

        val notLoading = loadState.source.refresh as? LoadState.NotLoading
        val isendOfPaginationReached = notLoading?.endOfPaginationReached ?: true
        if (isLoading && errorState == null) {
            lyt_no_connection?.hide()
        } else if (!isendOfPaginationReached) {
            rvFollowing?.show()
        }

        if (errorState != null) {
            val msgError = errorState.error.message
            if (adapter.itemCount <= 0) {
                if (msgError == ERROR_NOCONNECTION) {
                    lyt_no_connection?.show()
                    lyt_no_connection?.content?.text =
                        resources.getString(R.string.no_internet_conten)
                    rvFollowing?.hide()
                } else {
                    lyt_no_connection?.show()
                    lyt_no_connection?.content?.text = msgError
                    rvFollowing?.hide()
                }
            }

            Toast.makeText(
                requireContext(),
                "\uD83D\uDE28 Wooops $msgError",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object {
        fun newInstance(param1: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, param1)
                }
            }
    }
}