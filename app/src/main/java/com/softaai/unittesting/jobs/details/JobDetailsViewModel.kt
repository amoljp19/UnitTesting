package com.softaai.unittesting.jobs.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.softaai.unittesting.data.repository.JobsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class JobDetailsViewModel @AssistedInject constructor(
    private val jobsRepository: JobsRepository,
    @Assisted jobId: Int
) : ViewModel() {


    val job = jobsRepository.getJobItemById(jobId).asLiveData()


    @AssistedFactory
    interface JobDetailsViewModelFactory {
        fun create(jobId: Int): JobDetailsViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: JobDetailsViewModelFactory,
            jobId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(jobId) as T
            }
        }
    }




}