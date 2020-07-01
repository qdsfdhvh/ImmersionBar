package com.seiko.immersionbar.sample.fragment.one

import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.fragment.BaseImmersionFragment
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/5/12
 */
class CategoryOneFragment : BaseImmersionFragment() {

    override val layoutId: Int = R.layout.fragment_one_category

    public override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            keyboardEnable(true)
            statusBarDarkFont(true, 0.2f)
            navigationBarColor(getResColor(R.color.btn3))
        }
    }
}