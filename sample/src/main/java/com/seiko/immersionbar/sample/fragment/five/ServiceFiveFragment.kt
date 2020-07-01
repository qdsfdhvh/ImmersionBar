package com.seiko.immersionbar.sample.fragment.five

import android.os.Bundle
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/7/20
 */
class ServiceFiveFragment : BaseFiveFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_two_service
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            navigationBarColor(getResColor(R.color.btn2))
            keyboardEnable(false)
            statusBarDarkFont(true, 0.2f)
            navigationBarDarkIcon(true)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): ServiceFiveFragment {
            val args = Bundle()
            val fragment = ServiceFiveFragment()
            fragment.arguments = args
            return fragment
        }
    }
}