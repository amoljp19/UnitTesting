package com.softaai.unittesting.jobs.main.viewholder

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.softaai.unittesting.R
import com.softaai.unittesting.databinding.ItemJobBinding
import com.softaai.unittesting.model.JobsItemApiResponse


class JobViewHolder(private val binding: ItemJobBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        jobItem: JobsItemApiResponse,
        onItemClicked: (JobsItemApiResponse, ImageView) -> Unit
    ) {
        binding.title.text = jobItem.title
        binding.company.text = jobItem.company
        binding.location.text = jobItem.location

        binding.imageView.load(jobItem.companyLogo) {
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_broken_image)
        }

        binding.root.setOnClickListener {
            onItemClicked(jobItem, binding.imageView)
        }
    }
}