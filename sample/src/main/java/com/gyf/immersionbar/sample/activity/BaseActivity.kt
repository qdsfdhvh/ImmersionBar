package com.gyf.immersionbar.sample.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.AppManager
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.getResColor

/**
 * Activity基类
 *
 * @author geyifeng
 * @date 2017/5/9
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
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
        immersionBar {
            navigationBarColor(getResColor(R.color.colorPrimary))
        }
    }

    protected open fun initData() {}
    protected open fun initView() {}
    protected open fun setListener() {}
}