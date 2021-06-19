package com.softaai.unittesting.display.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.softaai.unittesting.R
import com.softaai.unittesting.data.remote.State
import com.softaai.unittesting.display.main.adapter.JobListAdapter
import com.softaai.unittesting.model.JobsItemApiResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mViewModel: MainViewModel by viewModels()

    lateinit var recyclerView : RecyclerView
    lateinit var adapter : JobListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAllJobs()

        recyclerView = findViewById<RecyclerView>(R.id.jobsRecyclerView)
        adapter = JobListAdapter(this::onItemClicked)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        mViewModel.jobsLiveData.value?.let { currentState ->
            if (!currentState.isSuccessful()) {
                getAllJobs()
            }
        }
        observeJobs()
    }

    private fun getAllJobs() = mViewModel.getAllJobs()


    private fun observeJobs() {
        mViewModel.jobsLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> Toast.makeText(applicationContext, "Loading...", Toast.LENGTH_SHORT).show()
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        adapter.submitList(state.data.toMutableList())
                    }
                    Toast.makeText(applicationContext, " " + state.data, Toast.LENGTH_SHORT).show()
                }
                is State.Error -> {
                    Toast.makeText(applicationContext, " " + state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onItemClicked(jobItem: JobsItemApiResponse, imageView: ImageView) {
        val jobId = jobItem.jobId ?: run {
            Toast.makeText(applicationContext, "Unable to launch details", Toast.LENGTH_SHORT)
                .show()
            return
        }
        //ToDo open detail fragment need to implement
    }

}