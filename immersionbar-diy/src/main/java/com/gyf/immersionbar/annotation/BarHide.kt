package com.gyf.immersionbar.annotation

import androidx.annotation.IntDef

/**
 * bar的状态
 */
@IntDef(
    BarHide.FLAG_HIDE_STATUS_BAR,
    BarHide.FLAG_HIDE_NAVIGATION_BAR,
    BarHide.FLAG_HIDE_BAR,
    BarHide.FLAG_SHOW_BAR
)
@Retention(AnnotationRetention.SOURCE)
annotation class BarHide {
    companion object {
        // 隐藏状态栏
        const val FLAG_HIDE_STATUS_BAR = 0
        // 隐藏导航栏
        const val FLAG_HIDE_NAVIGATION_BAR = 1
        // 隐藏状态栏和导航栏
        const val FLAG_HIDE_BAR = 2
        // 显示状态栏和导航栏
        const val FLAG_SHOW_BAR = 3
    }
}