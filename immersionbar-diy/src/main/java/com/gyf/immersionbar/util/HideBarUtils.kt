package com.gyf.immersionbar.util

import android.view.View
import com.gyf.immersionbar.annotation.BarHide

/**
 * 隐藏或显示状态栏和导航栏。
 */
internal fun hideBarFlag(@BarHide code: Int, uiFlags: Int): Int {
    return when(code) {
        BarHide.FLAG_HIDE_BAR -> {
            uiFlags or (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.INVISIBLE)
        }
        BarHide.FLAG_HIDE_STATUS_BAR -> {
            uiFlags or (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.INVISIBLE)
        }
        BarHide.FLAG_HIDE_NAVIGATION_BAR -> {
            uiFlags or (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }
        BarHide.FLAG_SHOW_BAR -> {
            uiFlags or View.SYSTEM_UI_FLAG_VISIBLE
        }
        else -> uiFlags
    }
}