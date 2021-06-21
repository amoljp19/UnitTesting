package com.softaai.unittesting.data.repository

import com.softaai.unittesting.data.local.LoginUserDao
import com.softaai.unittesting.model.LoginUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LoginUserRepository {
    suspend fun saveLoginUser()
    fun getLoginUserByCredentials(username: String, password: String): Flow<LoginUser?>
}

class DefaultLoginUserRepository @Inject constructor(
    private val loginUserDao: LoginUserDao
) : LoginUserRepository {

    override suspend fun saveLoginUser() {
        loginUserDao.addLoginUsers(
            loginUsers = listOf(
                LoginUser(1, "amol@gmail.com", "123456"),
                LoginUser(2, "amol19@gmail.com", "19191919"),
                LoginUser(3, "india@yahoo.com", "india1234")
            )
        )
    }


    override fun getLoginUserByCredentials(username: String, password: String): Flow<LoginUser?> {

        val data = loginUserDao.getLoginUserByCredentials(username, password)
        return data
    }

}

