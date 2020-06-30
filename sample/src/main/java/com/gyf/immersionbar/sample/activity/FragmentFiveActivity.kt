package com.gyf.immersionbar.sample.activity

import android.os.Bundle
import com.gyf.immersionbar.sample.AppManager
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.fragment.five.MainFragment
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 * @author geyifeng
 * @date 2017/8/12
 *
 * Fragmentation对于不显示的Fragment只会隐藏，不会冲走生命周期，暂不适配。
 */
class FragmentFiveActivity : SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        setContentView(R.layout.activity_fragmentation)
        if (findFragment(MainFragment::class.java) == null) {
            loadRootFragment(R.id.content, MainFragment.newInstance())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().removeActivity(this)
    }

    override fun onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置横向(和安卓4.x动画相同)
        return DefaultHorizontalAnimator()
    }
}