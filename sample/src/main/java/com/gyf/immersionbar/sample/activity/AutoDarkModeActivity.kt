package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import butterknife.BindView
import com.google.android.material.animation.ArgbEvaluatorCompat
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R

/**
 * 自动调整状态栏字体
 *
 * @author github.com/dengyuhan
 * @date 2018/12/16 03:56
 */
class AutoDarkModeActivity : BaseActivity(), SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.seek_bar)
    lateinit var seekBar: SeekBar

    @BindView(R.id.toolbar)
    lateinit var toolbar: View

    @BindView(R.id.tv_title)
    lateinit var tvTitle: TextView

    override val layoutId: Int= R.layout.activity_auto_status_font

    override fun initImmersionBar() {
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(Color.BLACK)
            navigationBarColor(Color.BLACK)
            autoDarkModeEnable(true)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setListener() {
        seekBar.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        val fraction = progress.toFloat() / 100
        val barColor = ArgbEvaluatorCompat.getInstance().evaluate(fraction, Color.BLACK, Color.WHITE)
        val textColor = ArgbEvaluatorCompat.getInstance().evaluate(fraction, Color.WHITE, Color.BLACK)
        toolbar.setBackgroundColor(barColor)
        tvTitle.setTextColor(textColor)
        immersionBar {
            statusBarColor(barColor)
            navigationBarColor(barColor)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
}