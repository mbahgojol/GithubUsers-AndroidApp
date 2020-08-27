package com.blank.githubuser.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.blank.githubuser.data.model.User
import com.blank.githubuser.ui.base.BaseViewModel
import com.blank.githubuser.ui.main.source.MainSource
import io.reactivex.Flowable

class MainViewModel @ViewModelInject constructor(
    private val mainSource: MainSource
) : BaseViewModel() {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    private var currentResultUsers: Flowable<PagingData<User>>? = null
    val resultUsers = MutableLiveData<PagingData<User>>()

    private fun currentUserResult(): Flowable<PagingData<User>> {
        val lastResult = currentResultUsers
        if (lastResult != null) return lastResult

        val newResult = Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                initialLoadSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 5,
                enablePlaceholders = false
            ), pagingSourceFactory = { mainSource }
        ).flowable.cachedIn(viewModelScope)
        currentResultUsers = newResult
        return newResult
    }

    fun getUsers() {
        currentUserResult()
            .subscribe {
                resultUsers.value = it
            }.autoDispose()
    }
}