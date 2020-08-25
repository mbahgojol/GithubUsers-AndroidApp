package com.blank.githubuser.ui.search

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
import com.blank.githubuser.ui.search.source.SearchSource
import com.blank.githubuser.utils.NetworkHelper
import io.reactivex.Flowable

class SearchViewModel @ViewModelInject constructor(
    private val githubRepository: GithubRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    private var currentResulstateSearch: Flowable<PagingData<User>>? = null
    val resulstateSearch = MutableLiveData<PagingData<User>>()

    fun searchUsers(username: String) {
        currentUserResult(username)
            .subscribe {
                resulstateSearch.value = it
            }.autoDispose()
    }

    private fun currentUserResult(q: String): Flowable<PagingData<User>> {
        val lastResult = currentResulstateSearch
        if (lastResult != null) return lastResult

        val newResult = Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ), pagingSourceFactory = { SearchSource(githubRepository, networkHelper, q) }
        ).flowable.cachedIn(viewModelScope)
        currentResulstateSearch = newResult
        return newResult
    }
}