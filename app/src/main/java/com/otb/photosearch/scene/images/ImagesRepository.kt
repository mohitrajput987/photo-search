package com.otb.photosearch.scene.images

import com.otb.photosearch.common.DataResult
import com.otb.photosearch.entity.Photo
import com.otb.photosearch.network.response.ErrorHandler
import com.otb.photosearch.network.service.FlickrApiService
import com.otb.preference.SharedPreferenceUtils

/**
 * Created by Mohit Rajput on 27/9/20.
 */

class ImagesRepository(private val flickrApiService: FlickrApiService, private val sharedPreference: SharedPreferenceUtils, private val photoDao: PhotoDao) : ImagesContract.Repository {
    companion object {
        const val KEY_SEARCH_KEYWORD = "search_keyword"
    }

    override suspend fun searchPhotos(searchText: String, pageNumber: Int) = flickrApiService.searchImages(searchText, pageNumber)

    override suspend fun getInitialPhotos(searchText: String): DataResult<List<Photo>> {
        val lastSearchKeyword = sharedPreference.getString(KEY_SEARCH_KEYWORD)
        val storedPhotos = photoDao.getPhotos()
        sharedPreference.saveString(KEY_SEARCH_KEYWORD, searchText)
        return if (!lastSearchKeyword.equals(searchText, ignoreCase = true) || storedPhotos.isEmpty()) {
            val response = flickrApiService.searchImages(searchText, 1)
            if (response.isSuccessful) {
                val flickrPhoto = response.body()!!.photos
                val photos = FlickrResponseToPhotosMapper().map(flickrPhoto.photos)
                photoDao.clear()
                photoDao.insertAll(photos)
                DataResult.DataSuccess(photos)
            } else {
                DataResult.DataError(ErrorHandler.getError(response))
            }
        } else {
            DataResult.DataSuccess(storedPhotos)
        }
    }

    override fun getLastSearchTerm() = sharedPreference.getString(KEY_SEARCH_KEYWORD)
}