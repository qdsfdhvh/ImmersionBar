package com.seiko.immersionbar.sample.fragment.one

import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.fragment.BaseImmersionFragment
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/5/12
 */
class MineOneFragment : BaseImmersionFragment() {

    override val layoutId: Int = R.layout.fragment_one_mine

    public override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            keyboardEnable(true)
            statusBarDarkFont(true)
            navigationBarColor(getResColor(R.color.btn1))
            navigationBarDarkIcon(true)
        }
    }
}