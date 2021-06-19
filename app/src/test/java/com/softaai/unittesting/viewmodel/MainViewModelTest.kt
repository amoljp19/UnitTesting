package com.softaai.unittesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.softaai.unittesting.data.remote.Resource
import com.softaai.unittesting.data.remote.State
import com.softaai.unittesting.data.repository.JobsRepository
import com.softaai.unittesting.display.main.MainViewModel
import com.softaai.unittesting.model.JobsItemApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class MainViewModelTest {

    @Mock
    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var jobsRepository: JobsRepository

    @Mock
    private lateinit var observer: Observer<State<List<JobsItemApiResponse>>>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = spy(MainViewModel(jobsRepository))
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test jobsLiveData should post loading state`() {

        coroutineTestRule.testDispatcher.runBlockingTest {

            //Given
            val data = mock<Flow<Resource<List<JobsItemApiResponse>>>>()

            whenever(jobsRepository.getAllJobs()).thenReturn(data)


            //When
            mainViewModel.getAllJobs()

            //Then
            mainViewModel.jobsLiveData.observeForever(observer)
            verify(observer).onChanged(ArgumentMatchers.refEq(State.loading()))

        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test jobsLiveData should post success state`() {

        coroutineTestRule.testDispatcher.runBlockingTest {

            //Given
            val data = getMockJobsItem()

            whenever(jobsRepository.getAllJobs()) doReturn flowOf(
                Resource.Success(
                    data
                )
            )


            //When
            mainViewModel.getAllJobs()


            //Then
            val observer = object : Observer<State<List<JobsItemApiResponse>>> {
                override fun onChanged(data1: State<List<JobsItemApiResponse>>) {
                    assertThat(data1, IsEqual(State.success(data)))
                    mainViewModel.jobsLiveData.removeObserver(this)
                }
            }
            mainViewModel.jobsLiveData.observeForever(observer)


        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `test jobsLiveData should post error state`() {

        coroutineTestRule.testDispatcher.runBlockingTest {

            //Given
            val errorMessage = "error message"

            whenever(jobsRepository.getAllJobs()) doReturn flowOf(
                Resource.Failed(
                    errorMessage
                )
            )


            //When
            mainViewModel.getAllJobs()


            //Then
            val observer = object : Observer<State<List<JobsItemApiResponse>>> {
                override fun onChanged(data1: State<List<JobsItemApiResponse>>) {
                    assertThat(data1, IsEqual(State.error(errorMessage)))
                    mainViewModel.jobsLiveData.removeObserver(this)
                }
            }
            mainViewModel.jobsLiveData.observeForever(observer)

        }
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