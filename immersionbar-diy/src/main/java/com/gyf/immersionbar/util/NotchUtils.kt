package com.gyf.immersionbar.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.DisplayCutout
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi

/**
 * 系统属性
 * The constant SYSTEM_PROPERTIES.
 */
private const val SYSTEM_PROPERTIES = "android.os.SystemProperties"

/**
 * 小米刘海
 * The constant NOTCH_XIAO_MI.
 */
private const val NOTCH_XIAO_MI = "ro.miui.notch"

/**
 * 华为刘海
 * The constant NOTCH_HUA_WEI.
 */
private const val NOTCH_HUA_WEI = "com.huawei.android.util.HwNotchSizeUtil"

/**
 * VIVO刘海
 * The constant NOTCH_VIVO.
 */
private const val NOTCH_VIVO = "android.util.FtFeature"

/**
 * OPPO刘海
 * The constant NOTCH_OPPO.
 */
private const val NOTCH_OPPO = "com.oppo.feature.screen.heteromorphism"

/**
 * 适配刘海屏
 */
@RequiresApi(Build.VERSION_CODES.P)
internal fun Window.fitsNotchScreen() {
    val lp = attributes
    lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    attributes = lp
}

/**
 * 判断是否是刘海屏
 */
internal fun Activity.hasNotchScreen(): Boolean {
    return hasNotchAtXiaoMi()
            || hasNotchAtHuaWei()
            || hasNotchAtOPPO()
            || hasNotchAtVIVO()
            || getDisplayCutout() != null
}

/**
 * 判断是否是刘海屏
 */
internal fun View.hasNotchScreen(): Boolean {
    return context.hasNotchAtXiaoMi()
            || context.hasNotchAtHuaWei()
            || context.hasNotchAtOPPO()
            || context.hasNotchAtVIVO()
            || getDisplayCutout() != null
}

/**
 * 获得刘海屏高度
 */
@SuppressLint("NewApi")
internal fun Activity.getNotchHeight(): Int {
    val displayCutout = getDisplayCutout()
    if (isPLater && displayCutout != null) {
        return when {
            isPortrait() -> {
                displayCutout.safeInsetTop
            }
            displayCutout.safeInsetLeft == 0 -> {
                displayCutout.safeInsetRight
            }
            else -> {
                displayCutout.safeInsetLeft
            }
        }
    } else {
        if (hasNotchAtXiaoMi()) {
            return getXiaoMiNotchHeight()
        }
        if (hasNotchAtHuaWei()) {
            return getHuaWeiNotchSize()[1]
        }
        val statusBarHeight = getStatusBarHeight()
        if (hasNotchAtVIVO()) {
            var notchHeight = 32f.dp2px(this)
            if (notchHeight < statusBarHeight) {
                notchHeight = statusBarHeight
            }
            return notchHeight
        }
        if (hasNotchAtOPPO()) {
            var notchHeight = 80
            if (notchHeight < statusBarHeight) {
                notchHeight = statusBarHeight
            }
            return notchHeight
        }
    }
    return 0
}

/**
 * 获得DisplayCutout
 */
private fun Activity.getDisplayCutout(): DisplayCutout? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        return window?.decorView?.rootWindowInsets?.displayCutout
    }
    return null
}

/**
 * 获得DisplayCutout
 */
private fun View.getDisplayCutout(): DisplayCutout? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        return rootWindowInsets?.displayCutout
    }
    return null
}

/**
 * Gets xiao mi notch height
 */
private fun Context.getXiaoMiNotchHeight(): Int {
    val resourceId = resources.getIdentifier(
        "notch_height", "dimen", "android")
    if (resourceId > 0) {
        return resources.getDimensionPixelSize(resourceId)
    }
    return 0
}

/**
 * Get hua wei notch size int [ ].
 */
private fun Context.getHuaWeiNotchSize(): IntArray {
    return classUse(intArrayOf(0, 0)) {
        val clz = classLoader.loadClass(
            "com.huawei.android.util.HwNotchSizeUtil")
        val get = clz.getMethod("getNotchSize")
        get.invoke(clz) as IntArray
    }
}

/**
 * 小米刘海屏判断
 */
private fun Context.hasNotchAtXiaoMi(): Boolean {
    if ("Xiaomi" == Build.MANUFACTURER) {
        return classUse(false) {
            @SuppressLint("PrivateApi")
            val clz = classLoader.loadClass(SYSTEM_PROPERTIES)
            val method = clz.getMethod("getInt",
                String::class.java, Int::class.java)
            method.invoke(clz, NOTCH_XIAO_MI, 0) as Int == 1
        }
    }
    return false
}

/**
 * 华为刘海屏判断
 */
private fun Context.hasNotchAtHuaWei(): Boolean {
    return classUse(false) {
        @SuppressLint("PrivateApi")
        val clz = classLoader.loadClass(NOTCH_HUA_WEI)
        val method = clz.getMethod("hasNotchInScreen")
        method.invoke(clz, 0x00000020) as Boolean
    }
}

/**
 * VIVO刘海屏判断
 */
private fun Context.hasNotchAtVIVO(): Boolean {
    return classUse(false) {
        @SuppressLint("PrivateApi")
        val clz = classLoader.loadClass(NOTCH_VIVO)
        val method = clz.getMethod("isFeatureSupport", Int::class.java)
        method.invoke(clz, 0x00000020) as Boolean
    }
}

/**
 * OPPO刘海屏判断
 */
private fun Context.hasNotchAtOPPO(): Boolean {
    return try {
        packageManager.hasSystemFeature(NOTCH_OPPO)
    } catch (ignored: Exception) {
        false
    }
}

/**
 * dp to px
 */
private fun Float.dp2px(context: Context): Int {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this,
            context.resources.displayMetrics).toInt()
}

private fun <R> classUse(default: R, block: () -> R): R {
    try {
        return block()
    } catch (ignored: ClassNotFoundException) {
    } catch (ignored: NoSuchMethodException) {
    } catch (ignored: Exception) {
    }
    return default
}