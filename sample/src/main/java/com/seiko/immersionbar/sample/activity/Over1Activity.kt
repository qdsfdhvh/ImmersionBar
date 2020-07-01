package com.seiko.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.widget.TextView
import butterknife.BindView
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/5/8
 */
class Over1Activity : BaseActivity() {

    @BindView(R.id.text)
    lateinit var textView: TextView

    override val layoutId: Int = R.layout.activity_over1

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            navigationBarColor(getResColor(R.color.colorPrimary))
            keyboardEnable(true)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        textView.text = "(不推荐使用此方案，是因为每个手机的状态栏高度不一样)在标题栏的上方增加View标签，高度根据android版本来判断，" +
            "在values-v19/dimens.xml文件里指定高度为25dp（20~25dp最佳，根据需求定），" +
            "在values/dimens.xml文件里，指定高度为0dp，详情参看此页面的实现"
    }
}