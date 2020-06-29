package com.gyf.immersionbar.sample.activity

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gyf.immersionbar.hasNavigationBar
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.Utils
import com.gyf.immersionbar.sample.utils.getResColor
import com.gyf.immersionbar.sample.utils.toast
import com.gyf.immersionbar.setStatusBarView

/**
 * @author gyf
 * @date 2016/10/24
 */
class PicAndColorActivity : BaseActivity(), SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.seek_bar)
    lateinit var seekBar: SeekBar

    @BindView(R.id.mIv)
    lateinit var mIv: ImageView

    override val layoutId: Int = R.layout.activity_pic_color

    override fun initImmersionBar() {
        super.initImmersionBar()
        setStatusBarView(findViewById(R.id.top_view))
        immersionBar {
            statusBarColor(Color.TRANSPARENT)
            navigationBarColor(Color.TRANSPARENT)
            fullScreen(true)
        }
    }

    override fun initView() {
        super.initView()
        Glide.with(this)
            .asBitmap()
            .load(Utils.getPic())
            .apply(RequestOptions().placeholder(R.mipmap.test))
            .into(mIv)
    }

    override fun setListener() {
        seekBar.setOnSeekBarChangeListener(this)
    }

    @OnClick(R.id.btn_status_color, R.id.btn_navigation_color, R.id.btn_color)
    fun onClick(v: View) {
        when (v.id) {
            R.id.btn_status_color -> {
                immersionBar {
                    statusBarColor(getResColor(R.color.colorAccent))
                }
            }
            R.id.btn_navigation_color -> {
                if (hasNavigationBar) {
                    immersionBar {
                        navigationBarColor(getResColor(R.color.colorAccent))
                    }
                } else {
                    toast("当前设备没有导航栏")
                }
            }
            R.id.btn_color -> {
                immersionBar {
                    statusBarColor(Color.TRANSPARENT)
                    navigationBarColor(Color.TRANSPARENT)
                }
            }
            else -> {}
        }
    }

    override fun onProgressChanged(
        seekBar: SeekBar,
        progress: Int,
        fromUser: Boolean
    ) {
        if (!fromUser) return
        val alpha = progress.toFloat() / 100
        immersionBar {
            statusBarColorTransform(getResColor(R.color.orange))
            navigationBarColorTransform(getResColor(R.color.colorPrimary))
            addViewSupportTransformColor(toolbar)
            barAlpha(alpha)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
}