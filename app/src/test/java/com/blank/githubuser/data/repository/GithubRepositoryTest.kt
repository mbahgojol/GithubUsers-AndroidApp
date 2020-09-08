package com.blank.githubuser.data.repository

import com.blank.githubuser.data.local.db.DbHelperlmpl
import com.blank.githubuser.data.remote.ApiHelperlmpl
import com.blank.githubuser.utils.ResultState
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GithubRepositoryTest {

    var apiHelperlmpl: ApiHelperlmpl? = null
    var dbHelperlmpl: DbHelperlmpl? = null
    var githubRepository: GithubRepository? = null

    @Before
    fun init() {
        apiHelperlmpl = Mockito.mock(ApiHelperlmpl::class.java)
        dbHelperlmpl = Mockito.mock(DbHelperlmpl::class.java)
        githubRepository = GithubRepository(apiHelperlmpl!!, dbHelperlmpl!!)
    }

    @Test
    fun `should run the getusers method`() {
        runBlocking {
            given(apiHelperlmpl?.getUsers(0, 10)).willReturn(Single.just(listOf()))
            githubRepository?.getUsers(0, 10)

            verify(apiHelperlmpl, atLeastOnce())?.getUsers(0, 10)
        }
    }

    @Test
    fun `should notrun the getusers method`() {
        runBlocking {
            given(apiHelperlmpl?.getUsers(0, 10)).willReturn(Single.just(listOf()))

            verify(apiHelperlmpl, never())?.getUsers(0, 10)
        }
    }

    @Test
    fun `should run the getuserbyusername method`() {
        runBlocking {
            given(apiHelperlmpl?.getUserByUsername("ghozimahdi")).willReturn(
                Single.just(
                    ResultState.Success(
                        ""
                    )
                )
            )
            githubRepository?.getUserByUsername("ghozimahdi")

            verify(apiHelperlmpl, atLeastOnce())?.getUserByUsername("ghozimahdi")
        }
    }

    @Test
    fun `should notrun the getuserbyusername method`() {
        runBlocking {
            given(apiHelperlmpl?.getUserByUsername("ghozimahdi")).willReturn(
                Single.just(
                    ResultState.Success(
                        ""
                    )
                )
            )

            verify(apiHelperlmpl, never())?.getUserByUsername("ghozimahdi")
        }
    }
}