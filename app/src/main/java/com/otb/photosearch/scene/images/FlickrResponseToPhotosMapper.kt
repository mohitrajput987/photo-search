package com.otb.photosearch.scene.images

import com.otb.photosearch.entity.Photo
import com.otb.photosearch.network.response.FlickrPhoto

/**
 * Created by Mohit Rajput on 27/9/20.
 */
class FlickrResponseToPhotosMapper {
    fun map(flickrResponse: List<FlickrPhoto>): List<Photo> {
        return flickrResponse.map { Photo(it.id, it.title, getUrl(it.farm, it.server, it.id, it.secret)) }
    }

    private fun getUrl(farm: String, server: String, id: String, secret: String) = "http://farm$farm.static.flickr.com/$server/${id}_$secret.jpg"
}