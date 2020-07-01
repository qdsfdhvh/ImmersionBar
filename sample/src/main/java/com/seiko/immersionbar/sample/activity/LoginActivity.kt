package com.seiko.immersionbar.sample.activity

import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.setTitleBar

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