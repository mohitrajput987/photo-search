package com.otb.photosearch.scene.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.otb.photosearch.R
import com.otb.photosearch.common.GlideUtils
import com.otb.photosearch.databinding.LayoutImageBinding
import com.otb.photosearch.entity.Photo

/**
 * Created by Mohit Rajput on 27/9/20.
 */
class ImageAdapter : PagedListAdapter<Photo, ImageAdapter.ImageViewHolder>(DiffUtilCallBack()) {

    inner class ImageViewHolder(private val binding: LayoutImageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.photo = photo
            GlideUtils.loadImage(binding.root.context, photo.url, binding.ivImage, R.drawable.ic_launcher_foreground)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemBinding: LayoutImageBinding = LayoutImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
                && oldItem.title == newItem.title
                && oldItem.url == newItem.url
    }
}