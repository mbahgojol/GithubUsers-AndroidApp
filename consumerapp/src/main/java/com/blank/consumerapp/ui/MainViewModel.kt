package com.blank.consumerapp.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.blank.consumerapp.data.GithubRepository
import com.blank.consumerapp.ui.base.BaseViewModel
import com.blank.consumerapp.utils.ResultState
import com.blank.consumerapp.utils.rx.singleIo

class MainViewModel @ViewModelInject constructor(private val repository: GithubRepository) :
    BaseViewModel() {

    val resultStateUser = MutableLiveData<ResultState>()

    fun loaduserFavorite() {
        repository.loadAll()
            .singleIo()
            .doOnSubscribe {
                resultStateUser.value = ResultState.Loading(true)
            }
            .doFinally {
                resultStateUser.value = ResultState.Loading(false)
            }
            .subscribe({
                resultStateUser.value = ResultState.Success(it)
            }, {
                resultStateUser.value = ResultState.Error(it)
            })
            .autoDispose()
    }
}