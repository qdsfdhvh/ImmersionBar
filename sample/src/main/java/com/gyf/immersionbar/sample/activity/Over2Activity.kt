package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.widget.TextView
import butterknife.BindView
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/5/8
 */
class Over2Activity : BaseActivity() {

    @BindView(R.id.text)
    lateinit var textView: TextView

    override val layoutId: Int = R.layout.activity_over2

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            statusBarColor(getResColor(R.color.colorPrimary))
            navigationBarColor(getResColor(R.color.colorPrimary))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        textView.text = "使用系统的fitsSystemWindows属性,在布局的根节点，" +
            "指定fitsSystemWindows为true，然后在代码中使用ImmersionBar指定状态栏的颜色，详情参看此页面的实现"
    }
}