package com.softaai.unittesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softaai.unittesting.data.remote.State
import com.softaai.unittesting.data.repository.JobsRepository
import com.softaai.unittesting.model.JobsItemApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val jobsRepository: JobsRepository) :
    ViewModel() {

    private val _jobsLiveData = MutableLiveData<State<List<JobsItemApiResponse>>>()

    val jobsLiveData: LiveData<State<List<JobsItemApiResponse>>> = _jobsLiveData

    fun getAllJobs() {
        viewModelScope.launch {
            jobsRepository.getAllJobs()
                .onStart { _jobsLiveData.value = State.loading() }
                .map { resource -> State.fromResource(resource) }
                .collect { state -> _jobsLiveData.value = state }
        }
    }
}
