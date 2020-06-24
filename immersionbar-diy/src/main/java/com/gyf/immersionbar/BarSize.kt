package com.gyf.immersionbar

import android.app.Activity
import com.gyf.immersionbar.util.*

class BarSize private constructor(
    val statusBarHeight: Int,
    val actionBarHeight: Int,
    val navigationHeight: Int,
    val navigationWidth: Int,
    val smallestWidthDp: Float,
    val isPortrait: Boolean
) {

    fun hasNavigationBar(): Boolean {
        return navigationHeight > 0
    }

    fun isNavigationAtBottom(): Boolean {
        return smallestWidthDp >= 600 || isPortrait
    }

    companion object {
        @JvmStatic
        internal fun create(activity: Activity): BarSize {
            return BarSize(
                statusBarHeight = activity.getStatusBarHeight(),
                actionBarHeight = activity.getActionBarHeight(),
                navigationHeight = activity.getNavigationBarHeight(),
                navigationWidth = activity.getNavigationBarWidth(),
                smallestWidthDp = activity.getSmallestWidthDp(),
                isPortrait = activity.isPortrait()
            )
        }
    }
}