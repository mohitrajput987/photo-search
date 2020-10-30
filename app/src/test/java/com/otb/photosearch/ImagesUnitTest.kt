package com.otb.photosearch

import com.otb.photosearch.common.DataResult
import com.otb.photosearch.network.response.FlickrPhoto
import com.otb.photosearch.network.response.FlickrResponse
import com.otb.photosearch.network.response.PhotoData
import com.otb.photosearch.scene.images.FlickrResponseToPhotosMapper
import com.otb.photosearch.scene.images.ImagesContract
import com.otb.photosearch.scene.images.ImagesViewModel
import com.otb.photosearch.scene.images.PhotoDao
import com.otb.photosearch.utils.InstantExecutorExtension
import com.otb.photosearch.utils.getOrAwaitValue
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class ImagesUnitTest {
    private lateinit var repository: ImagesContract.Repository
    private lateinit var photoDao: PhotoDao

    private val mapper = FlickrResponseToPhotosMapper()

    @Before
    fun setup() {
        repository = mockk()
        photoDao = mockk()
    }

    @Test
    fun `fetch images first time`() {
        val photos = listOf(
            FlickrPhoto("1", "me", "test_secret", "stage", "5", "test pic", 1, 0, 0),
            FlickrPhoto("2", "me", "test_secret", "stage", "6", "flower picture", 1, 1, 0)
        )
        val flickrResponse = FlickrResponse("success", PhotoData(1, 50, 10, 500, photos))
        every { repository.getLastSearchTerm() } returns "flower"
        coEvery { repository.getInitialPhotos("flower") } returns DataResult.DataSuccess(
            mapper.map(
                flickrResponse.photos.photos
            )
        )

        val imagesViewModel = ImagesViewModel(repository)
        runBlocking {
             assert(imagesViewModel.getPhotos().getOrAwaitValue().size == 2)
        }
        imagesViewModel.searchPhoto("hello")
        verify {
        }
        confirmVerified(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}