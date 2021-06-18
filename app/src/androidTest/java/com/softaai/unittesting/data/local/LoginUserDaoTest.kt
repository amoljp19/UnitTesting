package com.softaai.unittesting.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.softaai.unittesting.model.LoginUser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginUserDaoTest {

    private lateinit var mDatabase: JobsDatabase

    @Before
    fun init() {
        mDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            JobsDatabase::class.java
        ).build()
    }


    @Test
    @Throws(InterruptedException::class)
    fun insert_and_select_login_user() = runBlocking {

        val loginUsers = getMockLoginUser()

        mDatabase.getLoginUserDao().addLoginUsers(loginUsers)

        val dbLoginUsers = mDatabase.getLoginUserDao().getAllLoginUsers().first()

        MatcherAssert.assertThat(dbLoginUsers, CoreMatchers.equalTo(loginUsers))

    }


    fun getMockLoginUser() = listOf(
        LoginUser(1,"amol", "amol"),
        LoginUser(2, "abcd", "1234"),
        LoginUser(3,"india", "india")
    )
}