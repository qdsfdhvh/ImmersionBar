package com.gyf.immersionbar.sample.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.gyf.immersionbar.util.getStatusBarHeight

/**
 * @author geyifeng
 * @date 2019-04-24 18:53
 */
object ViewUtils {
    fun increaseViewHeightByStatusBarHeight(activity: Activity, view: View) {
        var lp = view.layoutParams
        if (lp == null) {
            lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        val layoutParams = lp as ViewGroup.MarginLayoutParams
        layoutParams.height += activity.getStatusBarHeight()
        view.layoutParams = layoutParams
    }
}