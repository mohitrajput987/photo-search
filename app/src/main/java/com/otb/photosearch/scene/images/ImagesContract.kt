package com.otb.photosearch.scene.images

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.otb.photosearch.common.DataResult
import com.otb.photosearch.common.ProgressStatus
import com.otb.photosearch.entity.Photo
import com.otb.photosearch.network.response.FlickrResponse
import retrofit2.Response

/**
 * Created by Mohit Rajput on 27/9/20.
 */
interface ImagesContract {
    interface ViewModel {
        fun getPhotos(): LiveData<PagedList<Photo>>
        fun getProgressStatus(): LiveData<ProgressStatus>
        fun searchPhoto(searchText: String)
    }

    interface Repository {
        suspend fun searchPhotos(searchText: String, pageNumber: Int): Response<FlickrResponse>
        suspend fun getInitialPhotos(searchText: String): DataResult<List<Photo>>
        fun getLastSearchTerm() : String
    }
}