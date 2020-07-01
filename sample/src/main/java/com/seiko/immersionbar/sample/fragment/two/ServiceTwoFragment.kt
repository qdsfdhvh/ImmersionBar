package com.seiko.immersionbar.sample.fragment.two

import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.fragment.BaseImmersionFragment
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/7/20
 */
class ServiceTwoFragment : BaseImmersionFragment() {

    override val layoutId: Int = R.layout.fragment_two_service

    public override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(getResColor(R.color.btn2))
            navigationBarDarkIcon(true)
            keyboardEnable(false)
        }
    }
}