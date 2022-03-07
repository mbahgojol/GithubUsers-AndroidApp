package com.example.githubuserapp.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.model.DetailUserResponse
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.detil.DetailActivity
import com.example.githubuserapp.utils.KEY_USER
import com.example.githubuserapp.utils.ResultState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val adapter by lazy {
        UserAdapter {
            Intent(this, DetailActivity::class.java).apply {
                putExtra(KEY_USER, it)
                startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.content.viewTreeObserver.addOnPreDrawListener(object :
                ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean =
                    when {
                        viewModel.mockDataLoading() -> {
                            binding.content.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        }
                        else -> false
                    }
            })
        }

        binding.rvMain.adapter = adapter
        binding.rvMain.setHasFixedSize(true)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        var myQuery = ""

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                myQuery = query.toString()
                viewModel.getUser(myQuery)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })

        viewModel.resultStateUser.observe(this) {
            when (it) {
                is ResultState.Success<*> -> {
                    val data = it.data as MutableList<DetailUserResponse>
                    adapter.submitList(data)
                }
                is ResultState.Error -> {
                    val snackbar = Snackbar.make(
                        binding.root,
                        it.e.message.toString(),
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.setAction("RETRY") {
                        viewModel.getUser(myQuery)
                    }
                    snackbar.show()
                }
                is ResultState.Loading -> {
                    binding.progresbar.isVisible = it.loading
                }
            }
        }

        viewModel.getUser()
    }
}