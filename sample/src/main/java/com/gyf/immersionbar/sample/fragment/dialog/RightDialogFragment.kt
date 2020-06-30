package com.gyf.immersionbar.sample.fragment.dialog

import android.content.res.Configuration
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.getResColor

/**
 * 右边DialogFragment
 *
 * @author geyifeng
 * @date 2017/7/28
 */
class RightDialogFragment : BaseDialogFragment() {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onStart() {
        super.onStart()
        mWindow.setGravity(Gravity.TOP or Gravity.END)
        mWindow.setWindowAnimations(R.style.RightAnimation)
        mWindow.setLayout(mWidthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun setLayoutId(): Int {
        return R.layout.dialog
    }

    override fun initImmersionBar() {
        immersionBar {
            navigationBarColor(getResColor(R.color.btn8))
            keyboardEnable(true)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mWindow.setLayout(mWidthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}