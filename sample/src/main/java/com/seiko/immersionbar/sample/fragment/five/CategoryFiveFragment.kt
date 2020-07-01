package com.seiko.immersionbar.sample.fragment.five

import android.os.Bundle
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/7/20
 */
class CategoryFiveFragment : BaseFiveFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_two_category
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            keyboardEnable(true)
            navigationBarColor(getResColor(R.color.btn1))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): CategoryFiveFragment {
            val args = Bundle()
            val fragment = CategoryFiveFragment()
            fragment.arguments = args
            return fragment
        }
    }
}