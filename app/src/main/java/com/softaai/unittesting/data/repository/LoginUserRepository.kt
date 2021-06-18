package com.softaai.unittesting.data.repository

import com.softaai.unittesting.data.local.LoginUserDao
import javax.inject.Inject

interface LoginUserRepository {
    fun saveLoginUser()
}

class DefaultLoginUserRepository @Inject constructor(
    private val loginUserDao: LoginUserDao
) : LoginUserRepository {

    override fun saveLoginUser() {
        TODO("Not yet implemented")
        //loginUserDao.addLoginUsers(loginUsers = listOf())
    }

}

