package com.gyf.immersionbar.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import com.gyf.immersionbar.diy.R

/**
 * MIUI导航栏显示隐藏标识位
 */
private const val MIUI_NAVIGATION_BAR_HIDE_SHOW = "force_fsg_nav_bar"

/**
 * EMUI导航栏显示隐藏标识位
 */
private const val EMUI_NAVIGATION_BAR_HIDE_SHOW = "navigationbar_is_min"

/**
 * 状态栏高度标识位
 */
private const val STATUS_BAR_HEIGHT = "status_bar_height"

/**
 * 导航栏 宽度&高度 标识
 */
private const val NAVIGATION_BAR_WIDTH = "navigation_bar_width"
private const val NAVIGATION_BAR_HEIGHT = "navigation_bar_height"
private const val NAVIGATION_BAR_HEIGHT_LANDSCAPE = "navigation_bar_height_landscape"

/**
 * 界面当前是否为竖屏
 */
fun Activity.isPortrait(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}

/**
 * 获得状态栏高度
 */
fun Activity.getStatusBarHeight(): Int {
    return getInternalDimensionSize(STATUS_BAR_HEIGHT)
}

/**
 * 获得标题栏高度
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
fun Activity.getActionBarHeight(): Int {
    val actionBar: View? = window.findViewById(R.id.action_bar_container)
    if (actionBar != null) {
        return actionBar.measuredHeight
    }
    val typedValue = TypedValue()
    theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)
    return TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)
}

/**
 * 获得导航栏宽度
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
fun Activity.getNavigationBarHeight(): Int {
    if (hasNavigationBar()) {
        return getInternalDimensionSize(if (isPortrait()) {
            NAVIGATION_BAR_HEIGHT
        } else {
            NAVIGATION_BAR_HEIGHT_LANDSCAPE
        })
    }
    return 0
}

/**
 * 获得导航栏宽度
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
fun Activity.getNavigationBarWidth(): Int {
    if (hasNavigationBar()) {
        return getInternalDimensionSize(NAVIGATION_BAR_WIDTH)
    }
    return 0
}

/**
 * 界面中是否有导航栏
 */
fun Activity.hasNavigationBar(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        // 判断小米手机是否开启了全面屏，开启了，直接返回false
        if (getGlobalBool(MIUI_NAVIGATION_BAR_HIDE_SHOW)) {
            return false
        }
        // 判断华为手机是否隐藏了导航栏，隐藏了，直接返回false
        if (isEMUI() && getGlobalBool(EMUI_NAVIGATION_BAR_HIDE_SHOW)) {
            return false
        }
    }

    // 其他手机根据屏幕真实高度与显示高度是否相同来判断
    val wm = windowManager
    val display = wm.defaultDisplay

    val realWidth: Int; val realHeight: Int
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        val realDisplayMetrics = DisplayMetrics()
        display.getRealMetrics(realDisplayMetrics)
        realWidth  = realDisplayMetrics.widthPixels
        realHeight = realDisplayMetrics.heightPixels
    } else {
        realWidth  = 0
        realHeight = 0
    }

    val displayMetrics = DisplayMetrics()
    display.getMetrics(displayMetrics)
    val displayWidth  = displayMetrics.widthPixels
    val displayHeight = displayMetrics.heightPixels

    return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0
}

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
private fun Activity.getGlobalBool(key: String): Boolean {
    return Settings.Global.getInt(contentResolver, key) != 0
}

private fun Context.getInternalDimensionSize(key: String): Int {
    try {
        val resourceId = Resources.getSystem().getIdentifier(key, "dimen", "android")
        if (resourceId > 0) {
            val sizeOne = resources.getDimensionPixelSize(resourceId)
            val sizeTwo = Resources.getSystem().getDimensionPixelSize(resourceId)
            return if (sizeTwo >= sizeOne) {
                sizeTwo
            } else {
                val densityOne = resources.displayMetrics.density
                val densityTwo = Resources.getSystem().displayMetrics.density
                val f = sizeOne * densityTwo / densityOne
                (if (f >= 0) f + 0.5f else f - 0.5f).toInt()
            }
        }
    } catch (ignored: Resources.NotFoundException) {
    }
    return 0
}