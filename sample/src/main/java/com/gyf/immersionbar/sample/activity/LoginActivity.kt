package com.gyf.immersionbar.sample.activity

import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.setTitleBar

/**
 * @author geyifeng
 */
class LoginActivity : BaseActivity() {

    override val layoutId: Int= R.layout.activity_login

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(findViewById(R.id.toolbar))
        immersionBar {
            keyboardEnable(true)
        }
    }
}