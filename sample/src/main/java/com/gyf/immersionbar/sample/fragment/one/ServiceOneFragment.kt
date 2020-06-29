package com.gyf.immersionbar.sample.fragment.one

import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.fragment.BaseImmersionFragment
import com.gyf.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/5/12
 */
class ServiceOneFragment : BaseImmersionFragment() {

    override val layoutId: Int = R.layout.fragment_one_service

    public override fun initImmersionBar() {
        immersionBar {
            navigationBarColor(getResColor(R.color.btn13))
        }
    }
}