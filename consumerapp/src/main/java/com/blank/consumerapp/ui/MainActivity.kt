package com.blank.consumerapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.blank.consumerapp.R
import com.blank.consumerapp.data.model.User
import com.blank.consumerapp.utils.ResultState
import com.blank.consumerapp.utils.observe
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvUser.setHasFixedSize(true)
        rvUser.adapter = adapter

        observe(viewModel.resultStateUser, this::manageResultStateUser)
        viewModel.loaduserFavorite()
    }

    private fun manageResultStateUser(state: ResultState) {
        when (state) {
            is ResultState.Success<*> -> {
                val users = state.data as List<User>
                adapter.setData(users)
            }

            is ResultState.Error -> {
                Snackbar
                    .make(parentMain, state.e.message.toString(), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.retry)) {
                        viewModel.loaduserFavorite()
                    }.show()
            }

            is ResultState.Loading -> {
                pbMain.isVisible = state.isloading
            }
        }
    }
}
