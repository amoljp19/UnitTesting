package com.softaai.unittesting.jobs.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.softaai.unittesting.R
import com.softaai.unittesting.data.remote.State
import com.softaai.unittesting.databinding.ActivityMainBinding
import com.softaai.unittesting.jobs.details.JobDetailsActivity
import com.softaai.unittesting.jobs.main.adapter.JobListAdapter
import com.softaai.unittesting.model.JobsItemApiResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val mViewModel: MainViewModel by viewModels()

    lateinit var mViewBinding: ActivityMainBinding

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: JobListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        getAllJobs()

        recyclerView = findViewById<RecyclerView>(R.id.jobsRecyclerView)
        adapter = JobListAdapter(this::onItemClicked)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        observeJobs()
    }

    private fun getAllJobs() = mViewModel.getAllJobs()


    private fun observeJobs() {
        mViewModel.jobsLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> Toast.makeText(
                    applicationContext,
                    "Loading...",
                    Toast.LENGTH_SHORT
                ).show()
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        adapter.submitList(state.data.toMutableList())
                    }
                    Toast.makeText(applicationContext, " " + state.data, Toast.LENGTH_SHORT).show()
                }
                is State.Error -> {
                    Toast.makeText(applicationContext, " " + state.message, Toast.LENGTH_SHORT)
                        .show()
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

        val intent = JobDetailsActivity.getStartIntent(this, jobId)
        startActivity(intent)
    }
}