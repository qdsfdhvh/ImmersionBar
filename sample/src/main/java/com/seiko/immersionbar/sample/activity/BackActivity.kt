package com.seiko.immersionbar.sample.activity

import android.os.Bundle
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.AppManager
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.getResColor
import com.seiko.immersionbar.setTitleBar
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