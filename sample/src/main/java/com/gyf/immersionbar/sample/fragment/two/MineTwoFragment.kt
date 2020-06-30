package com.gyf.immersionbar.sample.fragment.two

import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.fragment.BaseImmersionFragment
import com.gyf.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/7/20
 */
class MineTwoFragment : BaseImmersionFragment() {

    override val layoutId: Int = R.layout.fragment_two_mine

    public override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            navigationBarColor(getResColor(R.color.btn7))
            keyboardEnable(true)
        }
    }
}