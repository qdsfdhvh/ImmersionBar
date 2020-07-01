package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.Utils
import com.gyf.immersionbar.setTitleBar

/**
 * @author geyifeng
 * @date 2017/5/30
 */
class CoordinatorActivity : BaseActivity() {

    @BindView(R.id.detail_toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.fab)
    lateinit var fab: FloatingActionButton

    @BindView(R.id.text)
    lateinit var textView: TextView

    @BindView(R.id.mIv)
    lateinit var mIv: ImageView

    override val layoutId: Int = R.layout.activity_coordinator

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(toolbar)
        immersionBar {
            autoStatusBarDarkModeEnable(true)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        textView.text = "关于Snackbar在4.4和emui3.1上高度显示不准确的问题是由于沉浸式使用了系统的" +
            "WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS或者WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION" +
            "属性造成的，目前尚不知有什么解决办法"
        Glide.with(this)
            .asBitmap()
            .load(Utils.getPic())
            .apply(RequestOptions().placeholder(R.mipmap.test))
            .into(mIv)
    }

    override fun setListener() {
        fab.setOnClickListener {
            Snackbar.make(it, "我是Snackbar", Snackbar.LENGTH_LONG).show()
        }
        //toolbar返回按钮监听
        toolbar.setNavigationOnClickListener { finish() }
    }
}