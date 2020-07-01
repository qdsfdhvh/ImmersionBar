package com.seiko.immersionbar.sample.fragment.dialog

import android.content.res.Configuration
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.getResColor
import com.seiko.immersionbar.setTitleBar

/**
 * 左边DialogFragment
 *
 * @author geyifeng
 * @date 2017/7/28
 */
class LeftDialogFragment : BaseDialogFragment() {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onStart() {
        super.onStart()
        mWindow.setGravity(Gravity.TOP or Gravity.START)
        mWindow.setWindowAnimations(R.style.LeftAnimation)
        mWindow.setLayout(mWidthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun setLayoutId(): Int {
        return R.layout.dialog
    }

    override fun initImmersionBar() {
        setTitleBar(toolbar)
        immersionBar {
            navigationBarColor(getResColor(R.color.btn11))
            keyboardEnable(true)
            navigationBarWithKitkatEnable(resources.configuration.orientation
                == Configuration.ORIENTATION_PORTRAIT)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mWindow.setLayout(mWidthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT)
        immersionBar {
            navigationBarWithKitkatEnable(resources.configuration.orientation
                == Configuration.ORIENTATION_PORTRAIT)
        }
    }
}