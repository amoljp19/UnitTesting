package com.softaai.unittesting.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.softaai.unittesting.model.JobsItemApiResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class JobsDaoTest {

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
    fun insert_and_select_jobs_item() = runBlocking {

        val jobs = getMockJobsItem()

        mDatabase.getJobsDao().addJobsItem(jobs)

        val dbJobs = mDatabase.getJobsDao().getAllJobsItem().first()

        assertThat(dbJobs, equalTo(jobs))

    }

    @Test
    @Throws(InterruptedException::class)
    fun select_job_item_by_id() = runBlocking {

        val jobs = getMockJobsItem()

        mDatabase.getJobsDao().addJobsItem(jobs)

        var dbJobItem = mDatabase.getJobsDao().getJobItemByJobId(1).first()
        assertThat(dbJobItem, equalTo(jobs[0]))

        dbJobItem = mDatabase.getJobsDao().getJobItemByJobId(2).first()
        assertThat(dbJobItem, equalTo(jobs[1]))

    }


    @After
    fun cleanup() {
        mDatabase.close()
    }


    fun getMockJobsItem() = listOf(
        JobsItemApiResponse(
            1, "Company 1", "CompanyLogo 1", "CompanyUrl 1",
            "CreatedAt 1", "Description 1", "HowToApply 1", "JobId 1",
            "Location 1", "Title 1", "Type 1", "Url 1"
        ),

        JobsItemApiResponse(
            2, "Company 2", "CompanyLogo 2", "CompanyUrl 2",
            "CreatedAt 2", "Description 2", "HowToApply 2", "JobId 2",
            "Location 1", "Title 1", "Type 1", "Url 1"
        )
    )
}