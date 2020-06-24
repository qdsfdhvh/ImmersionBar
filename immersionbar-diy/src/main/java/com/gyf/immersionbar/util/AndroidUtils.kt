package com.gyf.immersionbar.util

import android.app.Activity
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