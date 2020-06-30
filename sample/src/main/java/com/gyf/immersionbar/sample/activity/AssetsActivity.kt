package com.gyf.immersionbar.sample.activity

import android.graphics.Color
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R

/**
 * @author geyifeng
 * @date 2019/3/25 2:47 PM
 */
class AssetsActivity : BaseActivity() {

    override val layoutId: Int = R.layout.activity_assets

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(Color.parseColor("#F56A3D"))
            keyboardEnable(true)
        }
    }
}