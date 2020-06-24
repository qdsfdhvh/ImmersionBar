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

/**
 * 显示状态栏
 */
fun Window.showStatusBar() {
    clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

/**
 * 获得刘海屏高度
 */
fun Activity.getNotchHeight(): Int {
    return getNotchHeight()
}

/**
 * 判断是否是刘海屏
 */
fun Activity.hasNotchScreen(): Boolean {
    return hasNotchScreen()
}

/**
 * 判断是否是刘海屏
 */
fun View.hasNotchScreen(): Boolean {
    return hasNotchScreen()
}

/**
 * 判断手机是否支持 状态栏 自动变色
 */
fun isSupportStatusBarDarkFont(): Boolean {
    return (isMIUI6()
        or isFlymeOS4()
        or (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M))
}

/**
 * 判断手机是否支持 导航栏 自动变色
 */
fun isSupportNavigationIconDark(): Boolean {
    return (isMIUI6()
        or (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O))
}

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