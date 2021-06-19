package com.softaai.unittesting.display.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.softaai.unittesting.databinding.ItemJobBinding
import com.softaai.unittesting.display.main.viewholder.JobViewHolder
import com.softaai.unittesting.model.JobsItemApiResponse


class JobListAdapter(
    private val onItemClicked: (JobsItemApiResponse, ImageView) -> Unit
) : ListAdapter<JobsItemApiResponse, JobViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = JobViewHolder(
        ItemJobBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) =
        holder.bind(getItem(position), onItemClicked)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<JobsItemApiResponse>() {
            override fun areItemsTheSame(oldItem: JobsItemApiResponse, newItem: JobsItemApiResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: JobsItemApiResponse, newItem: JobsItemApiResponse): Boolean =
                oldItem == newItem
        }
    }
}