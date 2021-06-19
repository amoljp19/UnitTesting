package com.softaai.unittesting.data.remote

import com.softaai.unittesting.model.JobsItemApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface JobsApiService {

    /*GitHub Jobs is deprecated! New jobs will not be posted from May 19, 2021. It will shut down entirely on August 19, 2021.
    Read more in the GitHub blog post.*/

    @GET("/positions.json?description=marketing")
    suspend fun getJobs(): Response<List<JobsItemApiResponse>>

    companion object {
        const val API_URL = "https://jobs.github.com"
    }

}