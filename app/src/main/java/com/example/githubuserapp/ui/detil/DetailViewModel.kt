package com.example.githubuserapp.ui.detil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.data.Repository
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val resultStateFollowing = MutableLiveData<ResultState>()
    val resultStateFollowers = MutableLiveData<ResultState>()
    val resultAddFavorite = MutableLiveData<Boolean>()
    val resultDeleteFavorite = MutableLiveData<Boolean>()

    private var isFavorite = false

    private val param = mapOf<String, Any>(
        "page" to 1,
        "per_page" to 1
    )

    fun findById(id: Int?, listener: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.findById(id ?: 0)
                .collect {
                    isFavorite = it
                    listener(isFavorite)
                }
        }
    }

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

    private fun addFavorite(user: User) {
        viewModelScope.launch {
            repository.addFavorite(user)
                .collect(resultAddFavorite::setValue)
        }
    }

    private fun deleteFavorite(user: User) {
        viewModelScope.launch {
            repository.deleteFavorite(user)
                .collect(resultDeleteFavorite::setValue)
        }
    }

    fun setFavorite(user: User?) {
        user?.let {
            if (isFavorite) {
                deleteFavorite(it)
            } else {
                addFavorite(it)
            }
            isFavorite = !isFavorite
        }
    }
}