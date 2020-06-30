package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.getResColor
import com.gyf.immersionbar.setTitleBar

/**
 * @author geyifeng
 * @date 2017/5/8
 */
class Over5Activity : BaseActivity() {

    @BindView(R.id.text)
    lateinit var textView: TextView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override val layoutId: Int = R.layout.activity_over5

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(toolbar)
        immersionBar {
            navigationBarColor(getResColor(R.color.colorPrimary))
            keyboardEnable(true)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        textView.text = "不需要在xml文件增加view给状态栏预留空间，重点是这个方法titleBar(toolbar)，实现原理：" +
            "根据状态栏高度动态设置标题栏（demo是ToolBar，也可以是其他的）高度，" +
            "设置标题栏距离顶部padding值的为状态栏的高度，记住xml标题栏的高度不能指定为wrap_content，" +
            "不然绘制的高度只有状态栏的高度"
    }
}