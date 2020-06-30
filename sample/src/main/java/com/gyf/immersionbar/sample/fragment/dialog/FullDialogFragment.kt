package com.gyf.immersionbar.sample.fragment.dialog

import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.getResColor
import com.gyf.immersionbar.setTitleBar

/**
 * 全屏DialogFragment
 *
 * @author geyifeng
 * @date 2017/7/28
 */
class FullDialogFragment : BaseDialogFragment() {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onStart() {
        super.onStart()
        mWindow.setWindowAnimations(R.style.RightAnimation)
    }

    override fun setLayoutId(): Int {
        return R.layout.dialog
    }

    override fun initImmersionBar() {
        setTitleBar(toolbar)
        immersionBar {
            statusBarDarkFont(true)
            navigationBarColor(getResColor(R.color.btn3))
            keyboardEnable(true)
        }
    }
}