package com.otb.photosearch.network.service

import com.otb.photosearch.network.response.FlickrResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mohit Rajput on 27/9/20.
 */
interface FlickrApiService {

    @GET("services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1")
    suspend fun searchImages(@Query("text") searchText: String = "tesla", @Query("page") page: Int = 1): Response<FlickrResponse>
}