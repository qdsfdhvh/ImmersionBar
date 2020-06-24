package com.gyf.immersionbar.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import com.gyf.immersionbar.diy.R

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
 * view 设置高度时的tag标记
 */
private const val IMMERSION_FITS_LAYOUT_OVERLAP  = 0x111

/**
 * 为 自定义标题栏 paddingTop和高度 增加fixHeight
 */
fun setTitleBar(@IntRange(from = 0) fixHeight: Int, vararg views: View) {
    views.filter { v ->
        v.getTag(IMMERSION_FITS_LAYOUT_OVERLAP) as? Int ?: 0 != fixHeight
    }.forEach {  v ->
        v.setTag(IMMERSION_FITS_LAYOUT_OVERLAP, fixHeight)
        val lp = v.layoutParams ?: ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT
            || lp.height == ViewGroup.LayoutParams.MATCH_PARENT
        ) {
            v.post {
                lp.height = v.height + fixHeight
                v.setPadding(
                    v.paddingLeft,
                    v.paddingTop + fixHeight,
                    v.paddingRight,
                    v.paddingBottom
                )
                v.layoutParams = lp
            }
        } else {
            lp.height += fixHeight
            v.setPadding(
                v.paddingLeft,
                v.paddingTop + fixHeight,
                v.paddingRight,
                v.paddingBottom
            )
            v.layoutParams = lp
        }
    }
}

/**
 * 为 自定义标题栏 marginTop 增加fixHeight
 */
fun setTitleBarMarginTop(@IntRange(from = 0) fixHeight: Int, vararg views: View) {
    views.filter { v ->
        v.getTag(IMMERSION_FITS_LAYOUT_OVERLAP) as? Int ?: 0 != fixHeight
    }.forEach { v ->
        v.setTag(IMMERSION_FITS_LAYOUT_OVERLAP, fixHeight)
        val lp = v.layoutParams ?: ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp as ViewGroup.MarginLayoutParams
        lp.setMargins(
            lp.leftMargin,
            lp.topMargin + fixHeight,
            lp.rightMargin,
            lp.bottomMargin
        )
        v.layoutParams = lp
    }
}

/**
 * 给 自定义状态栏 设置statusBar的高度
 */
fun setStatusBarView(@IntRange(from = 0) fixHeight: Int, v: View) {
    val fitsHeight = v.getTag(IMMERSION_FITS_LAYOUT_OVERLAP) as? Int ?: 0
    if (fitsHeight == fixHeight) return

    v.setTag(IMMERSION_FITS_LAYOUT_OVERLAP, fixHeight)
    val lp = v.layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        0
    )
    lp.height = fixHeight
    v.layoutParams = lp
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
    theme.resolveAttribute(android.R.attr.actionBarSize,
        typedValue, true)
    return TypedValue.complexToDimensionPixelSize(
        typedValue.data, resources.displayMetrics)
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

internal fun Activity.getSmallestWidthDp(): Float {
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getRealMetrics(metrics)
    val widthDp = metrics.widthPixels / metrics.density
    val heightDp = metrics.heightPixels / metrics.density
    return widthDp.coerceAtMost(heightDp)
}

/**
 * 界面中是否有导航栏
 */
private fun Activity.hasNavigationBar(): Boolean {
    // 判断小米手机是否开启了全面屏，开启了，直接返回false
    if (getGlobalBool(MIUI_NAVIGATION_BAR_HIDE_SHOW)) {
        return false
    }
    // 判断华为手机是否隐藏了导航栏，隐藏了，直接返回false
    if (isEMUI() && getGlobalBool(EMUI_NAVIGATION_BAR_HIDE_SHOW)) {
        return false
    }

    // 其他手机根据屏幕真实高度与显示高度是否相同来判断
    val wm = windowManager
    val display = wm.defaultDisplay

    val realDisplayMetrics = DisplayMetrics()
    display.getRealMetrics(realDisplayMetrics)
    val realWidth  = realDisplayMetrics.widthPixels
    val realHeight = realDisplayMetrics.heightPixels

    val displayMetrics = DisplayMetrics()
    display.getMetrics(displayMetrics)
    val displayWidth  = displayMetrics.widthPixels
    val displayHeight = displayMetrics.heightPixels

    return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0
}

private fun Activity.getGlobalBool(key: String): Boolean {
    return Settings.Global.getInt(contentResolver, key) != 0
}

private fun Context.getInternalDimensionSize(key: String): Int {
    try {
        val resourceId = Resources.getSystem().getIdentifier(
            key, "dimen", "android")
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