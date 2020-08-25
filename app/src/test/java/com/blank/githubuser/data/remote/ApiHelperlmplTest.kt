package com.blank.githubuser.data.remote

import org.junit.Test
import org.mockito.Mockito

class ApiHelperlmplTest {

    var apiService: ApiService? = null
    var apiHelperlmpl: ApiHelperlmpl? = null

    fun init() {
        apiService = Mockito.mock(ApiService::class.java)
        apiHelperlmpl = ApiHelperlmpl(apiService!!)
    }

    @Test
    fun getUsers() {

    }

    @Test
    fun `should get response success when fetch getuserbyusername`() {

    }
}