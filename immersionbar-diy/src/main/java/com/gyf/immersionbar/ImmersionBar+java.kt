package com.gyf.immersionbar

import android.app.Activity
import android.view.View
import com.gyf.immersionbar.util.getStatusBarHeight
import  com.gyf.immersionbar.util.setTitleBar

/**
 * 兼容Java写法
 */
open class JavaFun {

    companion object {
        @JvmStatic
        fun setTitleBar(activity: Activity, vararg views: View) {
            setTitleBar(activity.getStatusBarHeight(), *views)
        }
    }

}