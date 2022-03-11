package com.example.githubuserapp.ui.detil.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.databinding.FragmentFollowsBinding
import com.example.githubuserapp.ui.detil.DetailActivity
import com.example.githubuserapp.ui.detil.DetailViewModel
import com.example.githubuserapp.ui.main.UserAdapter
import com.example.githubuserapp.utils.KEY_USER
import com.example.githubuserapp.utils.ResultState
import com.google.android.material.snackbar.Snackbar

class FollowsFragment : Fragment() {

    private lateinit var binding: FragmentFollowsBinding
    private val viewModel by activityViewModels<DetailViewModel>()
    private var type = 0
    private val adapter by lazy {
        UserAdapter {
            Intent(requireActivity(), DetailActivity::class.java).apply {
                putExtra(KEY_USER, it)
                startActivity(this)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollow.adapter = adapter
        binding.rvFollow.setHasFixedSize(true)
        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())

        when (type) {
            FOLLOWERS -> {
                viewModel.resultStateFollowers.observe(
                    viewLifecycleOwner,
                    this::manageResultFollows
                )
            }
            FOLLOWING -> {
                viewModel.resultStateFollowing.observe(
                    viewLifecycleOwner,
                    this::manageResultFollows
                )
            }
        }
    }

    private fun manageResultFollows(state: ResultState) {
        when (state) {
            is ResultState.Success<*> -> {
                val data = state.data as MutableList<User>
                adapter.submitList(data)
            }
            is ResultState.Error -> {
                val snackbar = Snackbar.make(
                    binding.root,
                    state.e.message.toString(),
                    Snackbar.LENGTH_SHORT
                )
                snackbar.show()
            }
            is ResultState.Loading -> {
                binding.progresbar.isVisible = state.loading
            }
        }
    }

    companion object {
        const val FOLLOWING = 0
        const val FOLLOWERS = 1

        fun newInstances(type: Int): FollowsFragment =
            FollowsFragment().apply {
                this.type = type
            }
    }
}