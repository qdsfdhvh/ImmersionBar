package com.seiko.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/5/8
 */
class Over3Activity : BaseActivity() {

    @BindView(R.id.text)
    lateinit var textView: TextView

    @BindView(R.id.btn)
    lateinit var button: Button

    override val layoutId: Int = R.layout.activity_over3

    private var fitsSystemWindows = true

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            fitsSystemWindows(fitsSystemWindows)
            statusBarColor(getResColor(R.color.colorPrimary))
            navigationBarColor(getResColor(R.color.colorPrimary))
            keyboardEnable(true)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        textView.text = "使用ImmersionBar的fitsSystemWindows方法" +
            "指定fitsSystemWindows为true，然后指定状态栏的颜色，不然状态栏为透明色，很难看，详情参看此页面的实现"
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.btn)
    fun onClick(view: View) {
        when (view.id) {
            R.id.btn -> {
                if (fitsSystemWindows) {
                    fitsSystemWindows = false
                    immersionBar {
                        fitsSystemWindows(false)
                        transparentStatusBar()
                    }
                    button.text = "fitsSystemWindows动态演示:false"
                } else {
                    fitsSystemWindows = true
                    immersionBar {
                        fitsSystemWindows(true)
                        statusBarColor(getResColor(R.color.colorPrimary))
                    }
                    button.text = "fitsSystemWindows动态演示:true"
                }

//                val barParams: BarParams = ImmersionBar.with(this).getBarParams()
//                if (barParams.fits) {
//                    ImmersionBar.with(this).fitsSystemWindows(false).transparentStatusBar().init()
//                    button!!.text = "fitsSystemWindows动态演示:false"
//                } else {
//                    ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.colorPrimary).init()
//                    button!!.text = "fitsSystemWindows动态演示:true"
//                }
            }
            else -> {

            }
        }
    }
}