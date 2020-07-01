package com.seiko.immersionbar.util

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.ViewGroup

/**
 * 获得Activity最外层的布局
 */
internal fun Activity.getContentView(): ViewGroup {
    return findViewById(android.R.id.content)
}

internal fun View.getContentView(): ViewGroup {
    return findViewById(android.R.id.content)
}

/**
 * 界面当前是否为竖屏
 */
internal fun Activity.isPortrait(): Boolean {
    return resources.configuration.orientation ==
        Configuration.ORIENTATION_PORTRAIT
}

/**
 * Android P+
 */
internal val isPLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
internal val isOLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
internal val isMLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
internal val isLOLLIPOPLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP