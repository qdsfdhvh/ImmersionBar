package com.seiko.immersionbar.sample.fragment.two

import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.fragment.BaseImmersionFragment
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/7/20
 */
class CategoryTwoFragment : BaseImmersionFragment() {

    override val layoutId: Int = R.layout.fragment_two_category

    public override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            navigationBarColor(getResColor(R.color.btn1))
            keyboardEnable(true)
        }
    }
}