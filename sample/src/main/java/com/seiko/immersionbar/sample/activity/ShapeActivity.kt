package com.seiko.immersionbar.sample.activity

import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.getResColor
import com.seiko.immersionbar.setTitleBar

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