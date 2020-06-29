package com.gyf.immersionbar

import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.drawerlayout.widget.DrawerLayout
import com.gyf.immersionbar.annotation.BarHide
import com.gyf.immersionbar.util.checkFitsSystemWindows

@RequiresApi(Build.VERSION_CODES.KITKAT)
class FitsKeyboard(
    private val bar: ImmersionBar
) : ViewTreeObserver.OnGlobalLayoutListener {

    private val window: Window = bar.window
    private val decorView: View = bar.decorView
    private val contentView: ViewGroup = bar.decorView
    private var childView: View? = null
    private val paddingView: View

    private var paddingLeft = 0
    private var paddingTop = 0
    private var paddingRight = 0
    private var paddingBottom = 0

    private var isAddListener = false
    private var tempKeyboardHeight = 0

    init {
        childView = if (bar.isFragment) {
            bar.requireFragment().view
        } else {
            contentView.getChildAt(0)
        }
        childView?.let {
            if (it is DrawerLayout) {
                childView = it.getChildAt(0)
            }
        }
        childView?.let {
            paddingLeft = it.paddingLeft
            paddingTop = it.paddingTop
            paddingRight = it.paddingRight
            paddingBottom = it.paddingBottom
        }
        paddingView = if (childView != null) childView!! else contentView
    }

    fun enable(mode: Int) {
        window.setSoftInputMode(mode)
        if (!isAddListener) {
            decorView.viewTreeObserver.addOnGlobalLayoutListener(this)
            isAddListener = true
        }
    }

    fun disable() {
        if (isAddListener) {
            val view = childView
            if (view != null) {
                paddingView.setPadding(
                    paddingLeft,
                    paddingTop,
                    paddingRight,
                    paddingBottom
                )
            } else {
                paddingView.setPadding(
                    bar.paddingLeft,
                    bar.paddingTop,
                    bar.paddingRight,
                    bar.paddingBottom
                )
            }
        }
    }

    fun cancel() {
        if (isAddListener) {
            decorView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            isAddListener = false
        }
    }

    override fun onGlobalLayout() {
        val barSize = bar.barSize

        var bottom = 0
        val navigationBarHeight = if (barSize.isNavigationAtBottom) {
            barSize.navigationHeight
        } else {
            barSize.navigationWidth
        }
        var isPop = false

        val rect = Rect()
        decorView.getWindowVisibleDisplayFrame(rect)
        var keyboardHeight = paddingView.height - rect.bottom
        if (keyboardHeight != tempKeyboardHeight) {
            tempKeyboardHeight = keyboardHeight
            if (checkFitsSystemWindows(contentView)) {
                if (childView != null) {
                    if (bar.barConfig.isSupportActionBar) {
                        keyboardHeight += barSize.actionBarHeight + barSize.statusBarHeight
                    }
//                    if (bar.barConfig.fits) {
//                        keyboardHeight += barSize.statusBarHeight
//                    }
                    if (keyboardHeight > navigationBarHeight) {
                        bottom = keyboardHeight + paddingBottom
                        isPop = true
                    }
                    paddingView.setPadding(
                        paddingLeft,
                        paddingTop,
                        paddingRight,
                        bottom
                    )
                } else {
                    bottom = bar.paddingBottom
                    keyboardHeight -= navigationBarHeight
                    if (keyboardHeight > navigationBarHeight) {
                        bottom = keyboardHeight + navigationBarHeight
                        isPop = true
                    }
                    paddingView.setPadding(
                        bar.paddingLeft,
                        bar.paddingTop,
                        bar.paddingRight,
                        bottom
                    )
                }
            } else {
                keyboardHeight -= navigationBarHeight
                if (keyboardHeight > navigationBarHeight) {
                    isPop = true
                }
            }
            if (keyboardHeight < 0) {
                keyboardHeight = 0
            }
            // TODO keyboard change listener
            if (!isPop && bar.barConfig.barHideCode != BarHide.FLAG_SHOW_BAR) {
                bar.setBar()
            }
        }
    }

}