package com.blank.githubuser.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.blank.githubuser.data.repository.GithubRepository
import com.blank.githubuser.ui.base.BaseViewModel
import com.blank.githubuser.utils.ERROR_NOCONNECTION
import com.blank.githubuser.utils.NetworkHelper
import com.blank.githubuser.utils.ResultState

class DetailViewModel @ViewModelInject constructor(
    private val githubRepository: GithubRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private var resultState: ResultState? = null
    val mutableResultState = MutableLiveData<ResultState>()

    fun fetchUsers(username: String) {
        val lastResult = resultState
        if (lastResult == null) {
            if (networkHelper.isNetworkConnected()) {
                githubRepository.getUserByUsername(username)
                    .doOnSubscribe {
                        mutableResultState.value = ResultState.Loading(true)
                    }
                    .doFinally {
                        mutableResultState.value = ResultState.Loading(false)
                    }
                    .subscribe({
                        mutableResultState.value = it
                        resultState = it
                    }, {
                        mutableResultState.value = ResultState.Error(it)
                    }).autoDispose()
            } else {
                mutableResultState.value = ResultState.Error(Throwable(ERROR_NOCONNECTION))
            }
        } else {
            mutableResultState.value = lastResult
        }
    }
}