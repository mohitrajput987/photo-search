package com.otb.photosearch.scene.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.otb.photosearch.common.ProgressStatus
import com.otb.photosearch.entity.Photo
import kotlinx.coroutines.cancel

/**
 * Created by Mohit Rajput on 27/9/20.
 */
class ImagesViewModel(repository: ImagesContract.Repository) : ViewModel(), ImagesContract.ViewModel {
    private val imagesDataSourceFactory: ImagesDataSourceFactory = ImagesDataSourceFactory(repository, viewModelScope)
    private val progressLoadStatus: LiveData<ProgressStatus>
    private val photos: LiveData<PagedList<Photo>>

    init {
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .build()

        photos = LivePagedListBuilder<Int, Photo>(imagesDataSourceFactory, pagedListConfig)
                .build()

        progressLoadStatus = Transformations.switchMap(imagesDataSourceFactory.liveData, ImagesDataSource::getProgressLiveStatus)
    }

    override fun getPhotos() = photos

    override fun getProgressStatus() = progressLoadStatus

    override fun searchPhoto(searchText: String) {
        imagesDataSourceFactory.searchTerm = searchText
        photos.value?.dataSource?.invalidate()
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}