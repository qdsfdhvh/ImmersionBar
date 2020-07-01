package com.seiko.immersionbar.sample.fragment.dialog

import android.content.res.Configuration
import android.view.Gravity
import android.view.ViewGroup
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * 底部DialogFragment
 *
 * @author geyifeng
 * @date 2017/7/28
 */
class BottomDialogFragment : BaseDialogFragment() {

    override fun onStart() {
        super.onStart()
        mWindow.setGravity(Gravity.BOTTOM)
        mWindow.setWindowAnimations(R.style.BottomAnimation)
        mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mWidthAndHeight[1] / 2)
    }

    override fun setLayoutId(): Int {
        return R.layout.dialog
    }

    override fun initImmersionBar() {
        immersionBar {
            navigationBarColor(getResColor(R.color.cool_green_normal))
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mWidthAndHeight[1] / 2)
    }
}