package com.example.githubuserapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.data.Repository
import com.example.githubuserapp.utils.ResultState
import com.example.githubuserapp.utils.TimeForSplasScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _resultStateUser = MutableLiveData<ResultState>()
    val resultStateUser: LiveData<ResultState> get() = _resultStateUser
    private var dataLoaded = false

    fun getUser(username: String = "android") {
        val param = mapOf<String, Any>(
            "q" to username,
            "page" to 1,
            "per_page" to 1
        )

        viewModelScope.launch {
            repository.searchUser(param)
                .collect(_resultStateUser::setValue)
        }
    }

    fun mockDataLoading(): Boolean {
        viewModelScope.launch {
            delay(TimeForSplasScreen)
            dataLoaded = true
        }
        return dataLoaded
    }
}