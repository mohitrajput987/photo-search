package com.otb.photosearch.network.response

import retrofit2.Response

/**
 * Created by Mohit Rajput on 16/10/20.
 */
class ErrorHandler {
    companion object {
        fun getError(response: Response<out Any>): String {
            val errorBody = response.errorBody()?.string()
            return errorBody ?: "something went wrong"
        }
    }
}