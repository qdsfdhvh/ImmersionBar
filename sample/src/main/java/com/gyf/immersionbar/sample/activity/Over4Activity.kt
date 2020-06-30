package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.getResColor
import com.gyf.immersionbar.setStatusBarView

/**
 * @author geyifeng
 * @date 2017/5/8
 */
class Over4Activity : BaseActivity() {

    @BindView(R.id.text)
    lateinit var textView: TextView

    @BindView(R.id.status_bar_view)
    lateinit var view: View

    override val layoutId: Int = R.layout.activity_over4

    override fun initImmersionBar() {
        super.initImmersionBar()
        setStatusBarView(view)
        immersionBar {
            navigationBarColor(getResColor(R.color.colorPrimary))
            keyboardEnable(true)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        textView.text = "和方法一类似，都是在标题栏的上方增加View标签，但是高度指定为0dp，" +
            "也不需要在dimens.xml文件里做android版本的判断了，全部交给ImmersionBar来完成吧，" +
            "只需要使用ImmersionBar的statusBarView方法，然后在方法里指定view就可以啦，" +
            "详情参看此页面的实现"
    }
}