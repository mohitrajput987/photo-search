package com.otb.photosearch.scene.images

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.otb.photosearch.common.DataResult
import com.otb.photosearch.common.ProgressStatus
import com.otb.photosearch.entity.Photo
import com.otb.photosearch.network.response.ErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * Created by Mohit Rajput on 15/10/20.
 */

class ImagesDataSource(private val repository: ImagesContract.Repository, private val scope: CoroutineScope, private val searchTerm: String) : PageKeyedDataSource<Int, Photo>() {
    private val progressLiveStatus = MutableLiveData<ProgressStatus>()
    private val flickrResponseToPhotosMapper = FlickrResponseToPhotosMapper()

    fun getProgressLiveStatus() = progressLiveStatus

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Photo>) {
        scope.launch {
            when (val dataResult = repository.getInitialPhotos(searchTerm)) {
                is DataResult.DataSuccess -> {
                    progressLiveStatus.postValue(ProgressStatus.Success)
                    callback.onResult(dataResult.data, null, 2)
                }
                is DataResult.DataError -> progressLiveStatus.postValue(ProgressStatus.Error(dataResult.errorMessage))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        scope.launch {
            val response = repository.searchPhotos(searchTerm, params.key)
            if (response.isSuccessful) {
                progressLiveStatus.postValue(ProgressStatus.Success)
                val flickrPhoto = response.body()!!.photos
                callback.onResult(flickrResponseToPhotosMapper.map(flickrPhoto.photos), if (params.key == flickrPhoto.pages) null else params.key + 1)
            } else {
                progressLiveStatus.postValue(ProgressStatus.Error(ErrorHandler.getError(response)))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {

    }
}