package com.seiko.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.Utils
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/6/2
 */
class ActionBarActivity : BaseActivity() {

    @BindView(R.id.btn)
    lateinit var btn: Button

    @BindView(R.id.text)
    lateinit var text: TextView

    @BindView(R.id.mIv)
    lateinit var mIv: ImageView

    override val layoutId: Int = R.layout.activity_action_bar

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            keyboardEnable(true)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "结合actionBar使用"
        }
        text.text = "上面图片被actionBar遮挡住了,我想使布局从actionBar下面开始绘制，怎么办？"
        Glide.with(this).asBitmap().load(Utils.getPic())
            .apply(RequestOptions().placeholder(R.mipmap.test))
            .into(mIv)
    }

    @SuppressLint("SetTextI18n")
    override fun setListener() {
        btn.setOnClickListener {
            immersionBar {
                supportActionBar(true)
                statusBarColor(getResColor(R.color.colorPrimary))
            }
            text.text = "哈哈哈！解决啦！就问你惊不惊喜，意不意外，刺不刺激！！！" +
                "重点是这个方法supportActionBar(true)，实现原理，当为true时，布局距离顶部的" +
                "padding值为状态栏的高度+ActionBar的高度"
            btn.text = "解决啦"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ActionBarActivity", "onDestroy")
    }
}