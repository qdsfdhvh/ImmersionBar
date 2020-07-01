package com.gyf.immersionbar.sample.activity

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import butterknife.BindView
import com.gyf.immersionbar.*
import com.gyf.immersionbar.sample.R

/**
 * @author geyifeng
 */
class ParamsActivity : BaseActivity() {

    @BindView(R.id.mToolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.mTvStatus)
    lateinit var mTvStatus: TextView

    @BindView(R.id.mTvHasNav)
    lateinit var mTvHasNav: TextView

    @BindView(R.id.mTvNav)
    lateinit var mTvNav: TextView

    @BindView(R.id.mTvNavWidth)
    lateinit var mTvNavWidth: TextView

    @BindView(R.id.mTvAction)
    lateinit var mTvAction: TextView

    @BindView(R.id.mTvHasNotch)
    lateinit var mTvHasNotch: TextView

    @BindView(R.id.mTvInsets)
    lateinit var mTvInsets: TextView

    @BindView(R.id.mTvNotchHeight)
    lateinit var mTvNotchHeight: TextView

    @BindView(R.id.mTvFits)
    lateinit var mTvFits: TextView

    @BindView(R.id.mTvStatusDark)
    lateinit var mTvStatusDark: TextView

    @BindView(R.id.mTvNavigationDark)
    lateinit var mTvNavigationDark: TextView

    @BindView(R.id.mBtnStatus)
    lateinit var mBtnStatus: Button

    private var mIsHideStatusBar = false

    override val layoutId: Int = R.layout.activity_params

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(mToolbar)
//        immersionBar {
//            navigationBarColor(ContextCompat.getColor(this@ParamsActivity, R.color.btn13))
//        }
//        ImmersionBar.with(this).titleBar(mToolbar)
//                .setOnNavigationBarListener(show -> {
//                    initView();
//                    Toast.makeText(this, "导航栏" + (show ? "显示了" : "隐藏了"), Toast.LENGTH_SHORT).show();
//                })
//                .navigationBarColor(R.color.btn13).init();
    }

    override fun initData() {
        super.initData()
        mToolbar.title = intent.getCharSequenceExtra("title")
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        mTvStatus.text = getText(getTitle(mTvStatus) + statusBarHeight)
        mTvHasNav.text = getText(getTitle(mTvHasNav) + hasNavigationBar)
        mTvNav.text = getText(getTitle(mTvNav) + navigationBarHeight)
        mTvNavWidth.text = getText(getTitle(mTvNavWidth) + navigationBarWidth)
        mTvAction.text = getText(getTitle(mTvAction) + actionBarHeight)
        mTvHasNotch.post {
            mTvHasNotch.text = getText(getTitle(mTvHasNotch) + hasNotchScreen)
        }
        mTvNotchHeight.post {
            mTvNotchHeight.text = getText(getTitle(mTvNotchHeight) + notchHeight)
        }
        mTvFits.text = getText(getTitle(mTvFits) + checkFitsSystemWindows)
        mTvStatusDark.text = getText(getTitle(mTvStatusDark) + isSupportStatusBarDarkFont)
        mTvNavigationDark.text = getText(getTitle(mTvNavigationDark) + isSupportNavigationIconDark)

    }


    @SuppressLint("SetTextI18n")
    override fun setListener() {
        super.setListener()
        mBtnStatus.setOnClickListener {
            mIsHideStatusBar = if (!mIsHideStatusBar) {
                hideStatusBar()
                true
            } else {
                showStatusBar()
                false
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(mTvInsets) { _: View?, windowInsetsCompat: WindowInsetsCompat ->
            mTvInsets.text = getText(getTitle(mTvInsets) + windowInsetsCompat.systemWindowInsetTop)
            windowInsetsCompat.consumeSystemWindowInsets()
        }
    }



    private fun getText(text: String): SpannableString {
        val split = text.split(" {3}".toRegex()).toTypedArray()
        val spannableString = SpannableString(text)
        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.btn3))
        spannableString.setSpan(colorSpan, text.length - split[1].length, text.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannableString
    }

    private fun getTitle(textView: TextView?): String {
        val split = textView!!.text.toString().split(" {3}".toRegex()).toTypedArray()
        return split[0] + "   "
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        initView()
    }
}