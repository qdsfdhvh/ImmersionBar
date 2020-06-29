package com.gyf.immersionbar.sample.activity

import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.getResColor
import com.gyf.immersionbar.setTitleBar

/**
 * @author geyifeng
 * @date 2017/6/6
 */
class ShapeActivity : BaseActivity() {

    override val layoutId: Int = R.layout.activity_shape

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(findViewById(R.id.toolbar))
        immersionBar {
            navigationBarColor(getResColor(R.color.shape1))
        }
    }
}