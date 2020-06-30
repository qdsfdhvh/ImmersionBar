package com.gyf.immersionbar.sample.fragment.dialog

import android.content.res.Configuration
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.getResColor
import com.gyf.immersionbar.setTitleBar

/**
 * 顶部DialogFragment
 *
 * @author geyifeng
 * @date 2017/7/28
 */
class TopDialogFragment : BaseDialogFragment() {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onStart() {
        super.onStart()
        mWindow.setGravity(Gravity.TOP)
        mWindow.setWindowAnimations(R.style.TopAnimation)
        mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mWidthAndHeight[1] / 2)
    }

    override fun setLayoutId(): Int {
        return R.layout.dialog
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(toolbar)
        immersionBar {
            navigationBarColor(getResColor(R.color.btn4))
            navigationBarWithKitkatEnable(resources.configuration.orientation
                == Configuration.ORIENTATION_LANDSCAPE)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mWidthAndHeight[1] / 2)
        immersionBar {
            navigationBarWithKitkatEnable(resources.configuration.orientation
                == Configuration.ORIENTATION_LANDSCAPE)
        }
    }
}