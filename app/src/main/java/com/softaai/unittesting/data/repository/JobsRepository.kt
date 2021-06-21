package com.softaai.unittesting.data.repository

import androidx.annotation.MainThread
import com.softaai.unittesting.data.local.JobsDao
import com.softaai.unittesting.data.remote.JobsApiService
import com.softaai.unittesting.data.remote.Resource
import com.softaai.unittesting.model.JobsItemApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import retrofit2.Response
import javax.inject.Inject

interface JobsRepository {
    fun getAllJobs(): Flow<Resource<List<JobsItemApiResponse>>>
    fun getJobItemById(jobId: Int): Flow<JobsItemApiResponse>
}

class DefaultJobsRepository @Inject constructor(
    private val jobsDao: JobsDao,
    private val jobsApiService: JobsApiService
) : JobsRepository {

    override fun getAllJobs(): Flow<Resource<List<JobsItemApiResponse>>> {
        return object :
            NetworkBoundRepository<List<JobsItemApiResponse>, List<JobsItemApiResponse>>() {

            override suspend fun saveRemoteData(response: List<JobsItemApiResponse>) =
                jobsDao.addJobsItem(response)

            override fun fetchFromLocal(): Flow<List<JobsItemApiResponse>> =
                jobsDao.getAllJobsItem()

            override suspend fun fetchFromRemote(): Response<List<JobsItemApiResponse>> =
                jobsApiService.getJobs()
        }.asFlow()
    }


    @MainThread
    override fun getJobItemById(jobId: Int): Flow<JobsItemApiResponse> =
        jobsDao.getJobItemByJobId(jobId).distinctUntilChanged()
}