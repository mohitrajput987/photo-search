package com.otb.photosearch.common.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.otb.photosearch.entity.Photo
import com.otb.photosearch.scene.images.PhotoDao

/**
 * Created by Mohit Rajput on 17/10/20.
 */

@Database(entities = [Photo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "photo-search.db").build()
                }
            }
            return instance!!
        }
    }
}