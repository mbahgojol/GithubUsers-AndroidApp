package com.blank.githubuser.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.blank.githubuser.data.model.User
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
    val resultSaveDb = MutableLiveData<Boolean>()

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

    fun statusSave(id: Int, onSave: (Boolean) -> Unit) {
        githubRepository.findById(id)
            .subscribe({
                onSave(true)
            }, {
                onSave(false)
            }).autoDispose()
    }

    fun saveDb(click: Boolean, user: User) {
        if (click) {
            githubRepository.insertDb(user)
                .subscribe {
                    resultSaveDb.value = it
                }.autoDispose()
        } else {
            githubRepository.delete(user)
                .subscribe {
                    resultSaveDb.value = false
                }.autoDispose()
        }
    }
}