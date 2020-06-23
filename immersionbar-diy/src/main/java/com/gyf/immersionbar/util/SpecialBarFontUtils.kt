package com.gyf.immersionbar.util

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

private val mSetStatusBarColorIcon: Method? by lazy {
    try {
        Activity::class.java.getMethod("setStatusBarDarkIcon",
            Int::class.javaPrimitiveType)
    } catch (ignored: NoSuchMethodException) {
        null
    }
}

private val mSetStatusBarDarkIcon: Method? by lazy {
    try {
        Activity::class.java.getMethod("setStatusBarDarkIcon",
            Boolean::class.javaPrimitiveType)
    } catch (ignored: NoSuchMethodException) {
        null
    }
}

private val mStatusBarColorField: Field? by lazy {
    try {
        WindowManager.LayoutParams::class.java.getField("statusBarColor")
    } catch (ignored: NoSuchMethodException) {
        null
    }
}

private val SYSTEM_UI_FLAG_LIGHT_STATUS_BAR: Int by lazy {
    try {
        val field = View::class.java.getField("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR")
        field.getInt(null)
    } catch (ignored: NoSuchFieldException) {
        0
    } catch (ignored: IllegalAccessException) {
        0
    }
}

/**
 * 颜色转换成灰度值
 */
private fun Int.toGrey(): Int {
    val blue: Int = this and 0x000000FF
    val green: Int = this and 0x0000FF00 shr 8
    val red: Int = this and 0x00FF0000 shr 16
    return red * 38 + green * 75 + blue * 15 shr 7
}

/**
 * 判断颜色是否偏黑色
 */
private fun Int.isBlackColor(level: Int): Boolean {
    return toGrey() < level
}

/**
 * 设置状态栏字体图标颜色
 */
internal fun Activity.setStatusBarDarkIcon(@ColorInt color: Int) {
    val method = mSetStatusBarColorIcon
    if (method != null) {
        try {
            method.invoke(this, color)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    } else {
        val isDark = color.isBlackColor(50)
        val field = mStatusBarColorField
        if (field != null) {
            setStatusBarDarkIcon(isDark, isDark)
            setStatusBarDarkIcon(window, color)
        } else {
            setStatusBarDarkIcon(isDark)
        }
    }
}

/**
 * 设置状态栏字体图标颜色(只限全屏非activity情况)
 * @param color 颜色
 */
internal fun setStatusBarDarkIcon(window: Window, @ColorInt color: Int) {
    try {
        setStatusBarColor(window, color)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            setStatusBarDarkIcon(window.decorView, true)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * 设置状态栏字体图标颜色
 * @param isDark 是否使用深色
 */
private fun Activity.setStatusBarDarkIcon(isDark: Boolean, flag: Boolean = true) {
    val method = mSetStatusBarDarkIcon
    if (method != null) {
        try {
            method.invoke(this, isDark)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    } else if (flag) {
        setStatusBarDarkIcon(window, isDark)
    }
}

/**
 * 设置状态栏字体图标颜色(只限全屏非activity情况)
 */
private fun setStatusBarDarkIcon(window: Window, isDark: Boolean) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        changeMeizuFlag(window.attributes,
            "MEIZU_FLAG_DARK_STATUS_BAR_ICON", isDark)
    } else {
        setStatusBarDarkIcon(window.decorView, isDark)
        setStatusBarColor(window, 0)
    }
}

/**
 * 设置状态栏颜色
 */
private fun setStatusBarDarkIcon(view: View, isDark: Boolean) {
    val oldVis = view.systemUiVisibility
    var newVis = oldVis
    newVis = if (isDark) {
        newVis or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        newVis and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
    if (newVis != oldVis) {
        view.systemUiVisibility = newVis
    }
}

/**
 * 设置状态栏颜色
 */
private fun setStatusBarColor(window: Window, @ColorInt color: Int) {
    val windowParams = window.attributes
    mStatusBarColorField?.let { field ->
        try {
            val oldColor = field.getInt(windowParams)
            if (oldColor != color) {
                field.set(windowParams, color)
                window.attributes = windowParams
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}

private fun changeMeizuFlag(
    winParams: WindowManager.LayoutParams,
    flagName: String,
    dark: Boolean
): Boolean {
    try {
        val f = winParams.javaClass.getDeclaredField(flagName)
        f.isAccessible = true
        val bits = f.getInt(winParams)
        val f2 = winParams.javaClass.getDeclaredField("meizuFlags")
        f2.isAccessible = true
        var meizuFlags = f2.getInt(winParams)
        val oldFlags = meizuFlags
        meizuFlags = if (dark) {
            meizuFlags or bits
        } else {
            meizuFlags and bits.inv()
        }
        if (oldFlags != meizuFlags) {
            f2.setInt(winParams, meizuFlags)
            return true
        }
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: Throwable) {
        e.printStackTrace()
    }
    return false
}

/**
 * MIUI 状态栏&导航栏 字体黑色与白色标识位
 */
private const val IMMERSION_MIUI_STATUS_BAR_DARK = "EXTRA_FLAG_STATUS_BAR_DARK_MODE"
private const val IMMERSION_MIUI_NAVIGATION_BAR_DARK = "EXTRA_FLAG_NAVIGATION_BAR_DARK_MODE"

/**
 * 设置MIUI状态栏颜色
 */
internal fun Window.setStatusBarDarkWithMIUI(isDark: Boolean) {
    setMIUIBarDark(this, IMMERSION_MIUI_STATUS_BAR_DARK, isDark)
}

/**
 * 设置MIUI导航栏栏颜色
 */
internal fun Window.setNavigationBarDarkWithMIUI(isDark: Boolean) {
    setMIUIBarDark(this, IMMERSION_MIUI_NAVIGATION_BAR_DARK, isDark)
}

@SuppressLint("PrivateApi")
private fun setMIUIBarDark(window: Window, key: String, isDark: Boolean) {
    val clazz: Class<out Window> = window.javaClass
    try {
        val darkModeFlag: Int
        val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        val field = layoutParams.getField(key)
        darkModeFlag = field.getInt(layoutParams)
        val extraFlagField = clazz.getMethod("setExtraFlags",
            Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
        if (isDark) {
            //状态栏透明且黑色字体
            extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
        } else {
            //清除黑色字体
            extraFlagField.invoke(window, 0, darkModeFlag)
        }
    } catch (ignored: Exception) {
    }
}