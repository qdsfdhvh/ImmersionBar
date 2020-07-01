package com.seiko.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.getResColor
import com.seiko.immersionbar.setTitleBarMarginTop

/**
 * @author geyifeng
 * @date 2017/5/8
 */
class Over6Activity : BaseActivity() {

    @BindView(R.id.text)
    lateinit var textView: TextView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override val layoutId: Int = R.layout.activity_over6

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBarMarginTop(toolbar)
        immersionBar {
            navigationBarColor(getResColor(R.color.colorPrimary))
            statusBarColor(getResColor(R.color.colorPrimary))
            keyboardEnable(true)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        textView.text = "不需要在xml文件增加view给状态栏预留空间，重点是这个方法titleBarMarginTop(toolbar)，实现原理：" +
            "根据状态栏高度动态设置标题栏（demo是ToolBar，也可以是其他的）高度，" +
            "设置标题栏距离顶部MarginTop值的为状态栏的高度，然后在指定状态栏的颜色即可！"
    }
}