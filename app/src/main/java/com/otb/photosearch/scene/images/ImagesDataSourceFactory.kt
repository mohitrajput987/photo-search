package com.otb.photosearch.scene.images

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.otb.photosearch.entity.Photo
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Mohit Rajput on 15/10/20.
 */
class ImagesDataSourceFactory(private val repository: ImagesContract.Repository, private val scope: CoroutineScope) : DataSource.Factory<Int, Photo>() {
    var searchTerm = repository.getLastSearchTerm()
    val liveData = MutableLiveData<ImagesDataSource>()
    lateinit var imagesDataSource: ImagesDataSource

    override fun create(): DataSource<Int, Photo> {
        imagesDataSource = ImagesDataSource(repository, scope, searchTerm)
        liveData.postValue(imagesDataSource)
        return imagesDataSource
    }
}