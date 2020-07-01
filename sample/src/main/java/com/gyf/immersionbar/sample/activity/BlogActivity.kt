package com.gyf.immersionbar.sample.activity

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import butterknife.BindView
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/8/3
 */
class BlogActivity : BaseActivity() {

    @BindView(R.id.web_git)
    lateinit var mWebView: WebView

    private var blog: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        blog = intent?.getBundleExtra("bundle")?.getString("blog")
        super.onCreate(savedInstanceState)
    }

    override val layoutId: Int = R.layout.activity_git_hub

    override fun initView() {
        if ("github" == blog) {
            mWebView.loadUrl("https://github.com/gyf-dev/ImmersionBar")
        } else {
            mWebView.loadUrl("https://www.jianshu.com/p/2a884e211a62")
        }
        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.toString())
                return true
            }
        }
    }

    override fun initImmersionBar() {
        super.initImmersionBar()
        if ("github" == blog) {
            immersionBar {
                fitsSystemWindows(true)
                statusBarColor(getResColor(R.color.github_color))
            }
        } else {
            immersionBar {
                fitsSystemWindows(true)
                statusBarColor(Color.WHITE)
                statusBarDarkFont(true, 0.2f)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
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
    }
}