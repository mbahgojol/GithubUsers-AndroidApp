package com.example.githubuserapp.ui.detil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.data.Repository
import com.example.githubuserapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val resultStateFollowing = MutableLiveData<ResultState>()
    val resultStateFollowers = MutableLiveData<ResultState>()
    private val param = mapOf<String, Any>(
        "page" to 1,
        "per_page" to 30
    )

    fun getFollowing(username: String) {
        viewModelScope.launch {
            repository.listFollowing(username, param)
                .collect(resultStateFollowing::setValue)
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            repository.listFollower(username, param)
                .collect(resultStateFollowers::setValue)
        }
    }
}