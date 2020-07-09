package com.seiko.immersionbar

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.seiko.immersionbar.util.*
import com.seiko.immersionbar.util.getNotchHeight
import com.seiko.immersionbar.util.hasNotchScreen
import com.seiko.immersionbar.util.isFlymeOS4
import com.seiko.immersionbar.util.isMIUI6

/**
 * 隐藏状态栏
 */
fun hideStatusBar(window: Window) {
    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
}
fun Activity.hideStatusBar() = hideStatusBar(window)
fun Fragment.hideStatusBar() = hideStatusBar(requireActivity().window)

/**
 * 显示状态栏
 */
fun showStatusBar(window: Window) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}
fun Activity.showStatusBar() = showStatusBar(window)
fun Fragment.showStatusBar() = showStatusBar(requireActivity().window)

/**
 * 获得状态栏的高度
 */
val Activity.statusBarHeight get() = getStatusBarHeight()
val Fragment.statusBarHeight get() = requireActivity().statusBarHeight

/**
 * 获得标题栏的高度
 */
val Activity.actionBarHeight get() = getActionBarHeight()
val Fragment.actionBarHeight get() = requireActivity().actionBarHeight

/**
 * 获得导航栏的高度
 */
val Activity.navigationBarHeight get() = getNavigationBarHeight()
val Fragment.navigationBarHeight get() = requireActivity().navigationBarHeight

/**
 * 获得导航栏的宽度
 */
val Activity.navigationBarWidth get() = getNavigationBarWidth()
val Fragment.navigationBarWidth get() = requireActivity().navigationBarWidth

/**
 * 获得刘海屏高度
 */
val Activity.notchHeight get() = getNotchHeight()
val Fragment.notchHeight get() = requireActivity().notchHeight

/**
 * 判断是否是刘海屏
 */
val Activity.hasNotchScreen get() = hasNotchScreen()
val Fragment.hasNotchScreen get() = requireActivity().hasNotchScreen
val View.hasNotchScreen get() = hasNotchScreen()

/**
 * 判断是否有导航栏
 */
val Activity.hasNavigationBar get() = getNavigationBarHeight() > 0
val Fragment.hasNavigationBar get() = requireActivity().hasNavigationBar

/**
 * 检查Activity是否使用了android:fitsSystemWindows="true"属性
 */
val Activity.checkFitsSystemWindows get() = checkFitsSystemWindows(getContentView())
val Fragment.checkFitsSystemWindows get() = requireActivity().checkFitsSystemWindows

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
fun Activity.setTitleBar(vararg views: View) = setTitleBar(getStatusBarHeight(), *views)
fun Fragment.setTitleBar(vararg views: View) = setTitleBar(requireActivity().getStatusBarHeight(), *views)

/**
 * 为 自定义标题栏 marginTop 增加fixHeight
 */
fun Activity.setTitleBarMarginTop(vararg views: View) = setTitleBarMarginTop(getStatusBarHeight(), *views)
fun Fragment.setTitleBarMarginTop(vararg views: View) = setTitleBarMarginTop(requireActivity().getStatusBarHeight(), *views)

/**
 * 给 自定义状态栏 设置statusBar的高度
 */
fun Activity.setStatusBarView(view: View) = setStatusBarView(getStatusBarHeight(), view)
fun Fragment.setStatusBarView(view: View) = setStatusBarView(requireActivity().getStatusBarHeight(), view)