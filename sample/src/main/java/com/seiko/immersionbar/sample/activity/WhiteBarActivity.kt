package com.seiko.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.setTitleBar

/**
 * @author geyifeng
 * @date 2017/6/2
 */
class WhiteBarActivity : BaseActivity() {

    @BindView(R.id.btn)
    lateinit var btn: Button

    @BindView(R.id.text)
    lateinit var text: TextView

    override val layoutId: Int = R.layout.activity_white_status_bar

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(findViewById(R.id.toolbar))
        immersionBar {
            navigationBarColor(Color.WHITE)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setListener() {
        btn.setOnClickListener {
            immersionBar {
                statusBarDarkFont(true)
                navigationBarDarkIcon(true)
            }
            text.text = "A：对于状态栏重点在于statusBarDarkFont(true,0.2f)这个方法，" +
                "原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，" +
                "如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度；" +
                "对于导航栏重点在于navigationBarDarkIcon(true,0.2f)这个方法，" +
                "原理：如果当前设备支持导航栏图标变色，会设置导航栏图标为黑色，" +
                "如果当前设备不支持导航栏图标变色，会使当前导航栏加上透明度，否则不执行透明度；"
            btn.visibility = View.GONE
        }
    }
}