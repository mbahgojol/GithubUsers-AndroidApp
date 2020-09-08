package com.blank.githubuser.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blank.githubuser.R
import com.blank.githubuser.ui.base.BaseFragment
import com.blank.githubuser.ui.favorite.adapter.FavoriteAdapter
import com.blank.githubuser.utils.observe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite.*

@AndroidEntryPoint
class FavoriteFragment : BaseFragment() {
    override fun layoutId(): Int = R.layout.fragment_favorite
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFavorite.setHasFixedSize(true)
        rvFavorite.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

        observe(viewModel.mutableLiveFavorite) {
            val adapter = FavoriteAdapter(it) { user, addItem, position ->
                Snackbar
                    .make(clFavorite, getText(R.string.delUserMsg), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.undo)) {
                        addItem(user, position)
                    }.addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (event == DISMISS_EVENT_TIMEOUT) {
                                viewModel.deleteFavorite(user)
                            }
                        }
                    }).show()
            }
            rvFavorite.adapter = adapter
        }
        viewModel.fetchAllFavorite()
    }
}