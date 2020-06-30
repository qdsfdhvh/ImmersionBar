package com.gyf.immersionbar.sample.activity

import android.content.Intent
import android.view.View
import butterknife.OnClick
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.setTitleBar

/**
 * @author geyifeng
 * @date 2017/7/19
 */
class FragmentActivity : BaseActivity() {

    override val layoutId: Int = R.layout.activity_fragment

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(findViewById(R.id.toolbar))
    }

    @OnClick(R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four, R.id.btn_five)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_one -> startActivity(Intent(this, FragmentOneActivity::class.java))
            R.id.btn_two -> startActivity(Intent(this, FragmentTwoActivity::class.java))
            R.id.btn_three -> startActivity(Intent(this, FragmentThreeActivity::class.java))
            R.id.btn_four -> startActivity(Intent(this, FragmentFourActivity::class.java))
            R.id.btn_five -> startActivity(Intent(this, FragmentFiveActivity::class.java))
            else -> {
            }
        }
    }
}