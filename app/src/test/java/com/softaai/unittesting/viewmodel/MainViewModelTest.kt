package com.softaai.unittesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.softaai.unittesting.data.remote.Resource
import com.softaai.unittesting.data.remote.State
import com.softaai.unittesting.data.repository.JobsRepository
import com.softaai.unittesting.model.JobsItemApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
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


}