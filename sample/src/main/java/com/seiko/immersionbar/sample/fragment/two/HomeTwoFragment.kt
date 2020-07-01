package com.seiko.immersionbar.sample.fragment.two

import android.widget.ImageView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.fragment.BaseImmersionFragment
import com.seiko.immersionbar.sample.utils.Utils
import com.seiko.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/7/20
 */
class HomeTwoFragment : BaseImmersionFragment() {

    @BindView(R.id.mIv)
    lateinit var mIv: ImageView

    override val layoutId: Int = R.layout.fragment_two_home

    public override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            autoStatusBarDarkModeEnable(true)
            navigationBarColor(getResColor(R.color.colorPrimary))
            keyboardEnable(false)
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
}