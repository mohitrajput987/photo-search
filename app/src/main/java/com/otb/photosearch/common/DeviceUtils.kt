package com.otb.photosearch.common

import android.app.Activity
import android.util.DisplayMetrics
import kotlin.math.sqrt


/**
 * Created by Mohit Rajput on 18/10/20.
 */
object DeviceUtils {
    fun isTablet(activity: Activity): Boolean {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)

        val yInches = metrics.heightPixels / metrics.ydpi
        val xInches = metrics.widthPixels / metrics.xdpi
        val diagonalInches = sqrt(xInches * xInches + yInches * yInches.toDouble())
        return diagonalInches >= 6.5
    }
}