package com.gyf.immersionbar

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange

data class BarConfigBean(
    @ColorInt val statusBarColor: Int = Color.TRANSPARENT,
    @ColorInt val navigationBarColor: Int = Color.BLACK,
    @FloatRange(from = 0.0, to = 1.0) val statusBarAlpha: Float = 0.0f,
    @FloatRange(from = 0.0, to = 1.0) val statusBarTempAlpha: Float = 0.0f,
    @FloatRange(from = 0.0, to = 1.0) val navigationBarAlpha: Float = 0.0f,
    @FloatRange(from = 0.0, to = 1.0) val navigationBarTempAlpha: Float = 0.0f,
    val fullScreen: Boolean = false,
    val hideNavigationBar: Boolean = false,
    val statusBarDarkFont: Boolean = false,
    val navigationBarDarkIcon: Boolean = false,
    val autoStatusBarDarkModeEnable: Boolean = false,
    val autoNavigationBarDarkModeEnable: Boolean = false,
    val statusBarColorEnabled: Boolean = true
)