package com.softaai.unittesting.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softaai.unittesting.model.JobsItemApiResponse
import com.softaai.unittesting.model.LoginUser
import kotlinx.coroutines.flow.Flow

@Dao
interface LoginUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLoginUsers(loginUsers: List<LoginUser>?)

    @Query("DELETE FROM ${LoginUser.TABLE_NAME}")
    suspend fun deleteAllLoginUsers()

    @Query("SELECT * FROM ${LoginUser.TABLE_NAME}")
    fun getAllLoginUsers(): Flow<List<LoginUser>>

}