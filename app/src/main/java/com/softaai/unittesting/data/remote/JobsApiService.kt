package com.softaai.unittesting.data.remote

import com.softaai.unittesting.model.JobsApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface JobsApiService {

    @GET("/positions.json?description=marketing")
    suspend fun getJobs(): Response<JobsApiResponse>

    companion object {
        const val API_URL = "https://jobs.github.com"
    }

}