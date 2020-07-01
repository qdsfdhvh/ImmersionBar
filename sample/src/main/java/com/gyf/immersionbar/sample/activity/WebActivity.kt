package com.gyf.immersionbar.sample.activity

import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import butterknife.BindView
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.setTitleBar

/**
 * @author geyifeng
 * @date 2017/6/19
 */
class WebActivity : BaseActivity() {

    @BindView(R.id.web)
    lateinit var mWebView: WebView

    @BindView(R.id.line)
    lateinit var layout: LinearLayout

    override val layoutId: Int = R.layout.activity_web

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(findViewById(R.id.toolbar))
        immersionBar {
            // 解决软键盘与底部输入框冲突问题
            keyboardEnable(true)
        }
    }

    override fun initView() {
        mWebView.loadUrl("file:///android_asset/input_webview.html")
    }

    override fun onDestroy() {
        try {
            val parent = mWebView.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(mWebView)
            }
            mWebView.stopLoading()
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.settings.javaScriptEnabled = false
            mWebView.clearHistory()
            mWebView.loadUrl("about:blank")
            mWebView.removeAllViews()
            mWebView.destroy()
        } catch (ignored: Exception) {
        }
        super.onDestroy()
    }
}