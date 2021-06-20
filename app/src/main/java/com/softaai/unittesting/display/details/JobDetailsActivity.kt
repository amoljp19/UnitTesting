package com.softaai.unittesting.display.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.softaai.unittesting.databinding.ActivityJobDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class JobDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: JobDetailsViewModel.JobDetailsViewModelFactory

    private val mViewModel: JobDetailsViewModel by viewModels {
        val jobId = intent.extras?.getInt(KEY_JOB_ID)
            ?: throw IllegalArgumentException("`jobId` must be non-null")

        JobDetailsViewModel.provideFactory(viewModelFactory, jobId)
    }

    lateinit var mViewBinding: ActivityJobDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityJobDetailsBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        setSupportActionBar(mViewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        initJob()
    }

    private fun initJob() {
        mViewModel.job.observe(this) { job ->
            mViewBinding.jobContent.apply {
                title.text = job.title
                description.text = job.description
            }
            mViewBinding.imageView.load(job.companyLogo)
        }
    }


    companion object {
        private const val KEY_JOB_ID = "jobId"

        fun getStartIntent(
            context: Context,
            jobId: Int
        ) = Intent(context, JobDetailsActivity::class.java).apply {
            putExtra(
                KEY_JOB_ID,
                jobId
            )
        }
    }



}