package com.blank.githubuser.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.blank.githubuser.R
import com.blank.githubuser.ui.base.BaseFragment
import com.blank.githubuser.ui.main.adapter.MainAdapter
import com.blank.githubuser.ui.main.adapter.MainLoadStateAdapter
import com.blank.githubuser.utils.ERROR_NOCONNECTION
import com.blank.githubuser.utils.hide
import com.blank.githubuser.utils.observe
import com.blank.githubuser.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.no_internet.*
import kotlinx.android.synthetic.main.no_internet.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private val viewModel by viewModels<MainViewModel>()
    override fun layoutId(): Int = R.layout.fragment_main

    @Inject
    lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.addLoadStateListener(this::manageStateUser)
        rvMain.setHasFixedSize(true)
        rvMain.adapter = adapter.withLoadStateHeaderAndFooter(
            footer = MainLoadStateAdapter(adapter::retry),
            header = MainLoadStateAdapter(adapter::retry)
        )
        adapter.setListener { user, v ->
            val extras = FragmentNavigatorExtras(
                v[0] to ViewCompat.getTransitionName(v[0]).toString(),
                v[1] to ViewCompat.getTransitionName(v[1]).toString(),
                v[2] to ViewCompat.getTransitionName(v[2]).toString()
            )
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(user.login)
            findNavController().navigate(action, extras)
        }

        observe(viewModel.resultUsers) {
            adapter.submitData(lifecycle, it)
        }
        viewModel.getUsers()

        btnRetry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun manageStateUser(loadState: CombinedLoadStates) {
        val isLoading = loadState.source.refresh is LoadState.Loading
        pbMain?.isVisible = isLoading

        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
            ?: loadState.source.refresh as? LoadState.Error

        if (isLoading && errorState == null) {
            lyt_no_connection.hide()
        }

        if (errorState != null) {
            val msgError = errorState.error.message
            if (adapter.itemCount <= 0) {
                if (msgError == ERROR_NOCONNECTION) {
                    lyt_no_connection.show()
                    lyt_no_connection.content.text =
                        resources.getString(R.string.no_internet_conten)
                } else {
                    lyt_no_connection.show()
                    lyt_no_connection.content.text = msgError
                }
            }
            Toast.makeText(
                requireContext(),
                "\uD83D\uDE28 Wooops $msgError",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}