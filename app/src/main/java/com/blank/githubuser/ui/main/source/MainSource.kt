package com.blank.githubuser.ui.main.source

import androidx.paging.rxjava2.RxPagingSource
import com.blank.githubuser.data.model.User
import com.blank.githubuser.data.repository.GithubRepository
import com.blank.githubuser.ui.main.MainViewModel.Companion.NETWORK_PAGE_SIZE
import com.blank.githubuser.utils.ERROR_NOCONNECTION
import com.blank.githubuser.utils.NetworkHelper
import io.reactivex.Single
import javax.inject.Inject

private const val USER_STARTING_PAGE_INDEX = 1

class MainSource @Inject constructor(
    private val githubRepository: GithubRepository,
    private val networkHelper: NetworkHelper
) : RxPagingSource<Int, User>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, User>> {
        val position = params.key ?: USER_STARTING_PAGE_INDEX
        return when (networkHelper.isNetworkConnected()) {
            true -> githubRepository
                .getUsers(position, NETWORK_PAGE_SIZE)
                .map { toLoadResult(it, position) }
                .onErrorReturn { LoadResult.Error(it) }
            false -> Single.just(LoadResult.Error(Throwable(ERROR_NOCONNECTION)))
        }
    }

    private fun toLoadResult(
        data: List<User>,
        position: Int
    ): LoadResult<Int, User> = LoadResult.Page(
        data = data,
        prevKey = if (position == USER_STARTING_PAGE_INDEX) null else position,
        nextKey = if (data.isEmpty()) null else position.plus(1)
    )
}