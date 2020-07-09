package com.seiko.immersionbar.util

import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout

/**
 * 检查布局根节点是否使用了android:fitsSystemWindows="true"属性
 */
internal fun checkFitsSystemWindows(view: View): Boolean {
    if (view.fitsSystemWindows) return true
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val childView = view.getChildAt(i)
            if (childView is DrawerLayout) {
                if (checkFitsSystemWindows(childView)) {
                    return true
                }
            }
            if (childView.fitsSystemWindows) {
                return true
            }
        }
    }
    return false
}