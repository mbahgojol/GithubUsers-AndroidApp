package com.blank.githubuser.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.blank.githubuser.data.model.User
import com.blank.githubuser.data.repository.GithubRepository
import com.blank.githubuser.ui.base.BaseViewModel

class FavoriteViewModel @ViewModelInject constructor(private val githubRepository: GithubRepository) :
    BaseViewModel() {

    val mutableLiveFavorite = MutableLiveData<MutableList<User>>()

    fun fetchAllFavorite() {
        githubRepository.loadAll()
            .subscribe {
                mutableLiveFavorite.value = it
            }.autoDispose()
    }

    fun deleteFavorite(user: User) {
        githubRepository.delete(user)
            .subscribe()
            .autoDispose()
    }
}