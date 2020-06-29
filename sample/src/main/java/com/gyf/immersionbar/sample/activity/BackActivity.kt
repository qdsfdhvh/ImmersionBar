package com.gyf.immersionbar.sample.activity

import android.os.Bundle
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.AppManager
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.getResColor
import com.gyf.immersionbar.setTitleBar
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

/**
 * @author geyifeng
 * @date 2017/5/8
 */
class BackActivity : SwipeBackActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        setContentView(R.layout.activity_swipe_back)
        setTitleBar(findViewById(R.id.toolbar))
        immersionBar {
            navigationBarColor(getResColor(R.color.colorPrimary))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().removeActivity(this)
    }
}