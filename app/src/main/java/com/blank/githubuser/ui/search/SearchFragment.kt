package com.blank.githubuser.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.transition.Fade
import com.blank.githubuser.R
import com.blank.githubuser.ui.base.BaseFragment
import com.blank.githubuser.ui.search.adapter.SearchAdapter
import com.blank.githubuser.ui.search.adapter.SearchLoadStateAdapter
import com.blank.githubuser.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.no_internet.*
import kotlinx.android.synthetic.main.no_internet.view.*
import kotlinx.android.synthetic.main.no_item_search.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment() {
    private val viewModel by viewModels<SearchViewModel>()
    override fun layoutId() = R.layout.fragment_search

    @Inject
    lateinit var adapter: SearchAdapter
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = Fade()
        exitTransition = Fade()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.addLoadStateListener(this::manageStateSearchUsers)
        rvSearch.adapter = adapter.withLoadStateHeaderAndFooter(
            footer = SearchLoadStateAdapter(adapter::retry),
            header = SearchLoadStateAdapter(adapter::retry)
        )
        adapter.setListener { user, v ->
            val extras = FragmentNavigatorExtras(
                v[0] to ViewCompat.getTransitionName(v[0]).toString(),
                v[1] to ViewCompat.getTransitionName(v[1]).toString(),
                v[2] to ViewCompat.getTransitionName(v[2]).toString()
            )

            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(user.login)
            findNavController().navigate(action, extras)
        }

        findNavController().getBackStackEntry(R.id.searchFragment)
            .savedStateHandle
            .getLiveData<String>(SEARCH_USERS)
            .observe(viewLifecycleOwner, Observer {
                username = it
                viewModel.searchUsers(it)
            })

        observe(viewModel.resulstateSearch) {
            adapter.submitData(lifecycle, it)
            rvSearch.scheduleLayoutAnimation()
        }

        btnRetry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun manageStateSearchUsers(loadState: CombinedLoadStates) {
        val isLoading = loadState.source.refresh is LoadState.Loading
        pbSearch?.isVisible = isLoading

        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
            ?: loadState.source.refresh as? LoadState.Error

        val notLoading = loadState.source.refresh as? LoadState.NotLoading
        val isendOfPaginationReached = notLoading?.endOfPaginationReached ?: true
        if (isLoading && errorState == null) {
            lyt_no_connection.hide()
            ly_no_item.hide()
        } else if (!isendOfPaginationReached) {
            ly_no_item.hide()
            rvSearch.show()
        }

        if (errorState != null) {
            val msgError = errorState.error.message
            if (adapter.itemCount <= 0) {
                if (msgError == ERROR_NOCONNECTION) {
                    lyt_no_connection.show()
                    lyt_no_connection.content.text =
                        resources.getString(R.string.no_internet_conten)
                    rvSearch.hide()
                    ly_no_item.hide()
                } else {
                    lyt_no_connection.show()
                    lyt_no_connection.content.text = msgError
                    rvSearch.hide()
                    ly_no_item.hide()
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