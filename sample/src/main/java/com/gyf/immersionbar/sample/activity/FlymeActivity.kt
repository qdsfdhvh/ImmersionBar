package com.gyf.immersionbar.sample.activity

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.setStatusBarView

/**
 * @author geyifeng
 * @date 2017/5/31
 */
class FlymeActivity : BaseActivity() {

    @BindView(R.id.et)
    lateinit var et: EditText

    @BindView(R.id.btn)
    lateinit var btn: Button

    override val layoutId: Int = R.layout.activity_flyme

    override fun initImmersionBar() {
        super.initImmersionBar()
        setStatusBarView(findViewById(R.id.top_view))
    }

    override fun setListener() {
        btn.setOnClickListener {
            val s = "#" + et.text.toString()
            if (s.length == 7) {
                immersionBar {
                    flymeOSStatusBarFontColor(Color.parseColor(s))
                }
            } else {
                Toast.makeText(this@FlymeActivity, "请正确输入6位颜色值", Toast.LENGTH_SHORT).show()
            }
        }
    }
}