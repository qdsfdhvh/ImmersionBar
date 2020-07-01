package com.seiko.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.seiko.immersionbar.*
import com.seiko.immersionbar.sample.R
import kotlinx.android.synthetic.main.activity_params.*

/**
 * @author geyifeng
 * @date 2019/3/27 7:20 PM
 */
class KotlinActivity : BaseKotlinActivity(R.layout.activity_params) {

    private var mIsHideStatusBar = false

    override fun initImmersionBar() {
//        super.initImmersionBar()
        setTitleBar(mToolbar)
        immersionBar {
            navigationBarColor(ContextCompat.getColor(this@KotlinActivity, R.color.btn13))

        }
//        immersionBar {
//            titleBar(mToolbar)
//            navigationBarColor(R.color.btn13)
//            setOnNavigationBarListener {
//                initView()
//                val text = "导航栏${if (it) {
//                    "显示了"
//                } else {
//                    "隐藏了"
//                }}"
//                Toast.makeText(this@KotlinActivity, text, Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    override fun initData() {
        super.initData()
        mToolbar.title = intent.getCharSequenceExtra("title")
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        mTvStatus.text = "${mTvStatus.title}${statusBarHeight}".content()
        mTvHasNav.text = "${mTvHasNav.title}$hasNavigationBar".content()
        mTvNav.text = "${mTvNav.title}${navigationBarHeight}".content()
        mTvNavWidth.text = "${mTvNavWidth.title}${navigationBarWidth}".content()
        mTvAction.text = "${mTvAction.title}${actionBarHeight}".content()
        mTvHasNotch.post { mTvHasNotch.text = "${mTvHasNotch.title}${hasNotchScreen}".content() }
        mTvNotchHeight.post { mTvNotchHeight.text = "${mTvNotchHeight.title}${notchHeight}".content() }
        mTvFits.text = "${mTvFits.title}${checkFitsSystemWindows}".content()
        mTvStatusDark.text = "${mTvStatusDark.title}${isSupportStatusBarDarkFont}".content()
        mTvNavigationDark.text = "${mTvNavigationDark.title}${isSupportNavigationIconDark}".content()
    }

    @SuppressLint("SetTextI18n")
    override fun setListener() {
        mBtnStatus.setOnClickListener {
            mIsHideStatusBar = if (!mIsHideStatusBar) {
                hideStatusBar()
                true
            } else {
                showStatusBar()
                false
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(mTvInsets) { _, windowInsetsCompat ->
            mTvInsets.text = "${mTvInsets.title}${windowInsetsCompat.systemWindowInsetTop}".content()
            windowInsetsCompat.consumeSystemWindowInsets()
        }
    }

    private fun String.content(): SpannableString {
        val split = split("   ")
        return SpannableString(this).apply {
            val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this@KotlinActivity, R.color.btn3))
            setSpan(colorSpan, this.length - split[1].length, this.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }

    private val TextView.title get() = text.toString().split("   ")[0] + "   "

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        initView()
    }
}