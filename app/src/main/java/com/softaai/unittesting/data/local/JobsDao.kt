package com.softaai.unittesting.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softaai.unittesting.model.JobsItemApiResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface JobsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addJobsItem(jobsItem: List<JobsItemApiResponse>?)

    @Query("DELETE FROM ${JobsItemApiResponse.TABLE_NAME}")
    suspend fun deleteAllJobsItem()

    @Query("SELECT * FROM ${JobsItemApiResponse.TABLE_NAME} WHERE JOBID = :jobId")
    fun getJobItemByJobId(jobId: Int): Flow<JobsItemApiResponse>

    @Query("SELECT * FROM ${JobsItemApiResponse.TABLE_NAME}")
    fun getAllJobsItem(): Flow<List<JobsItemApiResponse>>

}