package com.gyf.immersionbar.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.TypedValue
import android.view.DisplayCutout
import android.view.View

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
 * 判断是否是刘海屏
 */
fun Activity.hasNotchScreen(): Boolean {
    return hasNotchAtXiaoMi()
            || hasNotchAtHuaWei()
            || hasNotchAtOPPO()
            || hasNotchAtVIVO()
            || getDisplayCutout() != null
}

/**
 * 判断是否是刘海屏
 */
fun View.hasNotchScreen(): Boolean {
    return context.hasNotchAtXiaoMi()
            || context.hasNotchAtHuaWei()
            || context.hasNotchAtOPPO()
            || context.hasNotchAtVIVO()
            || getDisplayCutout() != null
}

/**
 * 获得刘海屏高度
 */
fun Activity.getNotchHeight(): Int {
    val displayCutout = getDisplayCutout()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && displayCutout != null) {
        return when {
            resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT -> {
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
    val resourceId = resources.getIdentifier("notch_height", "dimen", "android")
    if (resourceId > 0) {
        return resources.getDimensionPixelSize(resourceId)
    }
    return 0
}

/**
 * Get hua wei notch size int [ ].
 */
private fun Context.getHuaWeiNotchSize(): IntArray {
    try {
        val clz = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil")
        val get = clz.getMethod("getNotchSize")
        return get.invoke(clz) as IntArray
    } catch (ignored: ClassNotFoundException) {
    } catch (ignored: NoSuchMethodException) {
    } catch (ignored: Exception) {
    }
    return intArrayOf(0, 0)
}

/**
 * 小米刘海屏判断
 */
private fun Context.hasNotchAtXiaoMi(): Boolean {
    if ("Xiaomi" == Build.MANUFACTURER) {
        try {
            @SuppressLint("PrivateApi")
            val clz = classLoader.loadClass(SYSTEM_PROPERTIES)
            val method = clz.getMethod("getInt", String::class.java, Int::class.java)
            return method.invoke(clz, NOTCH_XIAO_MI, 0) as Int == 1
        } catch (ignored: ClassNotFoundException) {
        } catch (ignored: NoSuchMethodException) {
        } catch (ignored: Exception) {
        }
    }
    return false
}

/**
 * 华为刘海屏判断
 */
private fun Context.hasNotchAtHuaWei(): Boolean {
    try {
        @SuppressLint("PrivateApi")
        val clz = classLoader.loadClass(NOTCH_HUA_WEI)
        val method = clz.getMethod("hasNotchInScreen")
        return method.invoke(clz, 0x00000020) as Boolean
    } catch (ignored: ClassNotFoundException) {
    } catch (ignored: NoSuchMethodException) {
    } catch (ignored: Exception) {
    }
    return false
}

/**
 * VIVO刘海屏判断
 */
private fun Context.hasNotchAtVIVO(): Boolean {
    try {
        @SuppressLint("PrivateApi")
        val clz = classLoader.loadClass(NOTCH_VIVO)
        val method = clz.getMethod("isFeatureSupport", Int::class.java)
        return method.invoke(clz, 0x00000020) as Boolean
    } catch (ignored: ClassNotFoundException) {
    } catch (ignored: NoSuchMethodException) {
    } catch (ignored: Exception) {
    }
    return false
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