package it.fantacalcio.sample.core.utils

import androidx.multidex.BuildConfig

object EnvironmentUtils {

    /*
     * Build Types
     */

    fun isDebug(): Boolean {
        return BuildConfig.BUILD_TYPE.contentEquals("debug")
    }

    fun isRelease(): Boolean {
        return BuildConfig.BUILD_TYPE.contentEquals("release")
    }

}