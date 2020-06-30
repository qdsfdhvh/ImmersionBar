package com.gyf.immersionbar.sample.fragment.five

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.Utils
import com.gyf.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/7/20
 */
class HomeFiveFragment : BaseFiveFragment() {

    @BindView(R.id.text)
    lateinit var text: TextView

    @BindView(R.id.mIv)
    lateinit var mIv: ImageView

    override fun getLayoutId(): Int {
        return R.layout.fragment_two_home
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            navigationBarColor(getResColor(R.color.colorPrimary))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        text.text = "使用Fragmentation框架要实现沉浸式，建议在加载Fragment的Activity中进行沉浸式初始化；解决布局重叠问题" +
            "，建议使用readme里第四种或者第五种方法解决，参考BaseFiveFragment的onViewCreated方法；" +
            "如果使用fitsSystemWindows(true)方法解决布局重叠问题，由于是采用单Activity多Fragment实现交互，Fragment之间切换就没那么丝滑了，" +
            "如果要在Fragment单独使用沉浸式，请在onSupportVisible方法中实现。参考BaseFiveFragment的onSupportVisible方法"
        Glide.with(this).asBitmap().load(Utils.getPic())
            .apply(RequestOptions().placeholder(R.mipmap.test))
            .into(mIv)
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFiveFragment {
            val args = Bundle()
            val fragment = HomeFiveFragment()
            fragment.arguments = args
            return fragment
        }
    }
}