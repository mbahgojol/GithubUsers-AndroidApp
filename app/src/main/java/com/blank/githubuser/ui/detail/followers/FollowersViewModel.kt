package com.blank.githubuser.ui.detail.followers

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.blank.githubuser.data.model.User
import com.blank.githubuser.data.repository.GithubRepository
import com.blank.githubuser.ui.base.BaseViewModel
import com.blank.githubuser.ui.detail.followers.source.FollowersSource
import com.blank.githubuser.utils.NetworkHelper
import io.reactivex.Flowable

class FollowersViewModel @ViewModelInject constructor(
    private val githubRepository: GithubRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {
    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    private var currentResult: Flowable<PagingData<User>>? = null
    val followersResult = MutableLiveData<PagingData<User>>()

    private fun currentUserResult(username: String): Flowable<PagingData<User>> {
        val lastResult = currentResult
        if (lastResult != null) return lastResult

        val newResult = Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 5,
                enablePlaceholders = false,
                maxSize = 30
            ), pagingSourceFactory = { FollowersSource(githubRepository, networkHelper, username) }
        ).flowable.cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

    fun getUsersFollowers(username: String) {
        currentUserResult(username)
            .subscribe {
                followersResult.value = it
            }.autoDispose()
    }
}