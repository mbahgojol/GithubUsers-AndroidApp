package com.example.githubuserapp.ui.main

import androidx.lifecycle.*
import com.example.githubuserapp.data.Repository
import com.example.githubuserapp.utils.ResultState
import com.example.githubuserapp.utils.TimeForSplasScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val resultStateUser = MutableLiveData<ResultState>()
    private var dataLoaded = false

    fun getUser(username: String = "android") {
        val param = mapOf<String, Any>(
            "q" to username,
            "page" to 1,
            "per_page" to 30
        )

        viewModelScope.launch {
            repository.searchUser(param)
                .collect(resultStateUser::setValue)
        }
    }

    fun getThemeSettings(): LiveData<Boolean> = repository.getThemeSetting().asLiveData()

    fun mockDataLoading(): Boolean {
        viewModelScope.launch {
            delay(TimeForSplasScreen)
            dataLoaded = true
        }
        return dataLoaded
    }
}