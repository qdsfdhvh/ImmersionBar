package com.gyf.immersionbar

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.util.*
import com.gyf.immersionbar.util.getNotchHeight
import com.gyf.immersionbar.util.hasNotchScreen
import com.gyf.immersionbar.util.isFlymeOS4
import com.gyf.immersionbar.util.isMIUI6

/**
 * 隐藏状态栏
 */
fun Window.hideStatusBar() {
    setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Activity.hideStatusBar() {
    window.hideStatusBar()
}

/**
 * 显示状态栏
 */
fun Window.showStatusBar() {
    clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun Activity.showStatusBar() {
    window.showStatusBar()
}

/**
 * 获得状态栏的高度
 */
val Activity.statusBarHeight get() = getStatusBarHeight()

/**
 * 获得标题栏的高度
 */
val Activity.actionBarHeight get() = getActionBarHeight()

/**
 * 获得导航栏的高度
 */
val Activity.navigationBarHeight get() = getNavigationBarHeight()

/**
 * 获得导航栏的宽度
 */
val Activity.navigationBarWidth get() = getNavigationBarWidth()

/**
 * 获得刘海屏高度
 */
val Activity.notchHeight get() = getNotchHeight()

/**
 * 判断是否是刘海屏
 */
val Activity.hasNotchScreen get() = hasNotchScreen()

/**
 * 判断是否是刘海屏
 */
val View.hasNotchScreen get() = hasNotchScreen()

/**
 * 判断是否有导航栏
 */
val Activity.hasNavigationBar get() = getNavigationBarHeight() > 0

/**
 * 检查Activity是否使用了android:fitsSystemWindows="true"属性
 */
val Activity.checkFitsSystemWindows get() = checkFitsSystemWindows(getContentView())

/**
 * 判断手机是否支持 状态栏 自动变色
 */
val isSupportStatusBarDarkFont get() = (isMIUI6() or isFlymeOS4()
    or (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M))

/**
 * 判断手机是否支持 导航栏 自动变色
 */
val isSupportNavigationIconDark get() = (isMIUI6()
    or (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O))

/**
 * 为 自定义标题栏 paddingTop和高度 增加fixHeight
 */
fun Activity.setTitleBar(vararg views: View) {
    setTitleBar(getStatusBarHeight(), *views)
}

/**
 * 为 自定义标题栏 paddingTop和高度 增加fixHeight
 */
fun Fragment.setTitleBar(vararg views: View) {
    setTitleBar(requireActivity().getStatusBarHeight(), *views)
}

/**
 * 为 自定义标题栏 marginTop 增加fixHeight
 */
fun Activity.setTitleBarMarginTop(vararg views: View) {
    setTitleBarMarginTop(getStatusBarHeight(), *views)
}

/**
 * 为 自定义标题栏 marginTop 增加fixHeight
 */
fun Fragment.setTitleBarMarginTop(vararg views: View) {
    setTitleBarMarginTop(requireActivity().getStatusBarHeight(), *views)
}

/**
 * 给 自定义状态栏 设置statusBar的高度
 */
fun Activity.setStatusBarView(view: View) {
    setStatusBarView(getStatusBarHeight(), view)
}

/**
 * 给 自定义状态栏 设置statusBar的高度
 */
fun Fragment.setStatusBarView(view: View) {
    setStatusBarView(requireActivity().getStatusBarHeight(), view)
}

///**
// * 根布局设置setFitsSystemWindows
// */
//fun Activity.setFitsSystemWindows(applySystemFits: Boolean) {
//    setFitsSystemWindows(
//        getContentView().getChildAt(0),
//        applySystemFits
//    )
//}
//
///**
// * 根布局设置setFitsSystemWindows
// */
//fun Fragment.setFitsSystemWindows(applySystemFits: Boolean) {
//    requireActivity().setFitsSystemWindows(applySystemFits)
//}