package com.otb.photosearch.scene.images

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.otb.photosearch.R
import com.otb.photosearch.common.DeviceUtils
import com.otb.photosearch.common.ProgressStatus
import com.otb.photosearch.databinding.ActivityImagesBinding
import kotlinx.android.synthetic.main.activity_images.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

val imagesModule = module {
    factory { ImagesRepository(get(), get(), get()) as ImagesContract.Repository }

    viewModel { ImagesViewModel(get()) }
}

class ImagesActivity : AppCompatActivity() {
    private val imagesViewModel: ImagesContract.ViewModel by viewModel<ImagesViewModel>()
    private lateinit var binding: ActivityImagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(imagesModule)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_images)
        init()
        setupViewModel()
    }

    private fun init() {
        val layoutManager = GridLayoutManager(this, if (DeviceUtils.isTablet(this)) 3 else 2)
        binding.rvPhotos.layoutManager = layoutManager
        binding.rvPhotos.adapter = ImageAdapter()

        btnSearch.setOnClickListener {
            val searchText = etSearch.text.toString()
            imagesViewModel.searchPhoto(searchText)
            tvTitle.text = getString(R.string.search_title, searchText)
        }
    }

    private fun setupViewModel() {
        imagesViewModel.getPhotos().observe(this, Observer {
            (rvPhotos.adapter as ImageAdapter).submitList(it)
        })

        imagesViewModel.getProgressStatus().observe(this, Observer {
            when (it) {
                is ProgressStatus.Loading -> displayLoading()
                is ProgressStatus.Success -> hideLoading()
                is ProgressStatus.Error -> displayError(it.errorMessage)
            }
        })
    }

    private fun displayLoading() {

    }

    private fun hideLoading() {

    }

    private fun displayError(errorMessage: String) {
        hideLoading()
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        unloadKoinModules(imagesModule)
        super.onDestroy()
    }
}