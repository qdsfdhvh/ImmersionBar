package com.gyf.immersionbar

import android.app.Dialog
import android.content.res.Configuration
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class ImmersionBar(
    private val activity: FragmentActivity,
    private val isFragment: Boolean = false,
    private val fragment: Fragment? = null,
    private val isDialog: Boolean = false,
    private val dialog: Dialog? = null
) {

    private val window = activity.window
    private val barConfig = BarConfigBean()
    private var parentImmersionBar: ImmersionBar? = null

    init {
        if (isFragment || isDialog) {
            parentImmersionBar = activity.immersionBar
        }
    }

    private fun requireFragment(): Fragment {
        return fragment ?: throw IllegalStateException("ImmersionBar $this does not have any fragment.")
    }

    private fun requireDialog(): Dialog {
        return dialog ?: throw IllegalStateException("ImmersionBar $this does not have any dialog.")
    }

    /**
     * 是否已经初始化
     */
    private var isInitialized = false

    private var paddingLeft = 0
    private var paddingTop = 0
    private var paddingRight = 0
    private var paddingBottom = 0

    fun onCreate() {
        // 更新Bar参数
        updateBarParams()
        // 设置沉浸式
        setBar()
        // 修正界面显示
        fitsWindows()
        // 适配软键盘与底部输入冲突问题
        fitsKeyboard()
        // 变色View
        transformView()
        isInitialized = true
    }

    fun onDestroy() {

    }

    fun onResume() {

    }

    fun onConfigurationChanged(newConfig: Configuration) {

    }

    /**
     * 更新Bar的参数
     */
    private fun updateBarParams() {
        adjustDarkModeParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            updateBarConfig()
            parentImmersionBar?.let {
                if (isFragment) {

                }
            }
        }
    }

    private fun setBar() {

    }

    private fun fitsWindows() {

    }

    private fun fitsKeyboard() {

    }

    private fun transformView() {

    }

    private fun adjustDarkModeParams() {

    }

    private fun updateBarConfig() {

    }

}