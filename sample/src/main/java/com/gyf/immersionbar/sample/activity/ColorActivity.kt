package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
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
class ColorActivity : BaseActivity() {

    @BindView(R.id.text_view)
    lateinit var textView: TextView

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.btn1)
    lateinit var btn1: Button

    @BindView(R.id.btn2)
    lateinit var btn2: Button

    @BindView(R.id.btn3)
    lateinit var btn3: Button

    @BindView(R.id.line1)
    lateinit var linearLayout: LinearLayout

    @BindView(R.id.seek_bar)
    lateinit var seekBar: SeekBar

    override val layoutId: Int = R.layout.activity_color

    override fun initImmersionBar() {
        setTitleBar(mToolbar)
        immersionBar {
            statusBarColor(getResColor(R.color.colorPrimary))
            statusBarColorTransform(getResColor(R.color.btn14))
            navigationBarColor(getResColor(R.color.btn8))
            navigationBarColorTransform(getResColor(R.color.btn3))
            addViewSupportTransformColor(mToolbar)
            addViewSupportTransformColor(btn1, getResColor(R.color.btn1), getResColor(R.color.btn4))
            addViewSupportTransformColor(btn2, getResColor(R.color.btn3), getResColor(R.color.btn12))
            addViewSupportTransformColor(btn3, getResColor(R.color.btn5), getResColor(R.color.btn10))
            addViewSupportTransformColor(linearLayout, getResColor(R.color.darker_gray), getResColor(R.color.btn5))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setListener() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val alpha = progress.toFloat() / 100
                textView.text = "透明度:" + alpha + "f"
                immersionBar {
                    barAlpha(alpha)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }
}