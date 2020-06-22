package com.gyf.immersionbar

import android.app.Activity
import android.view.Window
import android.view.WindowManager

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

fun Activity.getNotchHeight() {

}