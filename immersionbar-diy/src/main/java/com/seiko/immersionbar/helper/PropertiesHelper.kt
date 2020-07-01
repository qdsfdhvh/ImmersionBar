package com.seiko.immersionbar.helper

import android.app.Activity
import android.content.res.Configuration
import android.view.Surface
import com.seiko.immersionbar.BarSize
import com.seiko.immersionbar.hasNotchScreen
import com.seiko.immersionbar.notchHeight

class PropertiesHelper(
    private val activity: Activity,
    private val callback: (BarProperties) -> Unit
) : Runnable {

    private val barProperties = BarProperties()

    fun onConfigurationChanged(newConfig: Configuration) {
        barProperties.portrait = (newConfig.orientation ==
            Configuration.ORIENTATION_PORTRAIT)
        when(activity.windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_90 -> {
                barProperties.landscapeLeft = true
                barProperties.landscapeRight = false
            }
            Surface.ROTATION_270 -> {
                barProperties.landscapeLeft = false
                barProperties.landscapeRight = true
            }
            else -> {
                barProperties.landscapeLeft = false
                barProperties.landscapeRight = false
            }
        }
        activity.window.decorView.post(this)
    }

    override fun run() {
        val barSize = BarSize.create(activity)
        barProperties.statusBarHeight = barSize.statusBarHeight
        barProperties.hasNavigationBar = barSize.hasNavigationBar
        barProperties.navigationBarHeight = barSize.navigationHeight
        barProperties.navigationBarWidth = barSize.navigationWidth
        barProperties.actionBarHeight = barSize.actionBarHeight
        barProperties.notchScreen = activity.hasNotchScreen
        if (barProperties.notchScreen) {
            barProperties.notchHeight = activity.notchHeight
        }
        callback.invoke(barProperties)
    }
}

data class BarProperties(
    // 是否是竖屏
    var portrait: Boolean = false,
    // 是否为左横屏
    var landscapeLeft: Boolean = false,
    // 是否是右横屏
    var landscapeRight: Boolean = false,
    // 是否是刘海屏
    var notchScreen: Boolean = false,
    // 是否有导航栏
    var hasNavigationBar: Boolean = false,
    // 状态栏高度，刘海屏横竖屏有可能状态栏高度不一样
    var statusBarHeight: Int = 0,
    // 导航栏高度
    var navigationBarHeight: Int = 0,
    // 导航栏宽度
    var navigationBarWidth: Int = 0,
    // 刘海屏高度
    var notchHeight: Int = 0,
    // ActionBar高度
    var actionBarHeight: Int = 0
)