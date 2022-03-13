package com.example.githubuserapp.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.githubuserapp.data.Repository
import com.example.githubuserapp.data.model.User
import com.example.githubuserapp.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var listUserObserver: Observer<MutableList<User>>

    @Test
    fun testGetListUser_whenFetch_existData() {
        testCoroutineRule.runBlockingTest {
            val user = mock(User::class.java)

            doReturn(MutableLiveData(mutableListOf(user)))
                .`when`(repository)
                .listUser()

            val viewModel = FavoriteViewModel(repository)
            viewModel.listUser.observeForever(listUserObserver)

            verify(repository).listUser()
            verify(listUserObserver).onChanged(mutableListOf(user))

            viewModel.listUser.removeObserver(listUserObserver)
        }
    }

    @Test
    fun testGetListUser_whenFetch_noData() {
        testCoroutineRule.runBlockingTest {
            doReturn(MutableLiveData(emptyList<User>()))
                .`when`(repository)
                .listUser()

            val viewModel = FavoriteViewModel(repository)
            viewModel.listUser.observeForever(listUserObserver)

            verify(repository).listUser()
            verify(listUserObserver).onChanged(mutableListOf())

            viewModel.listUser.removeObserver(listUserObserver)
        }
    }
}