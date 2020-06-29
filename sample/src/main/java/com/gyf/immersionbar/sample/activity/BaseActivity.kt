package com.gyf.immersionbar.sample.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import butterknife.ButterKnife
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.AppManager
import com.gyf.immersionbar.sample.R

/**
 * Activity基类
 *
 * @author geyifeng
 * @date 2017/5/9
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var mTag = this.javaClass.simpleName
    @JvmField
    protected var mActivity: Activity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        mActivity = this
        setContentView(layoutId)
        //绑定控件
        ButterKnife.bind(this)
        //初始化沉浸式
        initImmersionBar()
        //初始化数据
        initData()
        //view与数据绑定
        initView()
        //设置监听
        setListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().removeActivity(this)
    }

    /**
     * 子类设置布局Id
     *
     * @return the layout id
     */
    protected abstract val layoutId: Int

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected open fun initImmersionBar() {
//        //设置共同沉浸式样式
//        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init()

        immersionBar {
            navigationBarColor(ContextCompat.getColor(this@BaseActivity, R.color.colorPrimary))
        }
    }

    protected open fun initData() {}
    protected open fun initView() {}
    protected open fun setListener() {}
}