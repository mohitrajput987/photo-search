package com.otb.photosearch.scene.images

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.otb.photosearch.entity.Photo

/**
 * Created by Mohit Rajput on 17/10/20.
 */

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo")
    suspend fun getPhotos(): List<Photo>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(photos: List<Photo>)

    @Query("DELETE FROM photo")
    suspend fun clear()
}