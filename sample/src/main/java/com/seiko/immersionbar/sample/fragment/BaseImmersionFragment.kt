package com.seiko.immersionbar.sample.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.setStatusBarView
import com.seiko.immersionbar.setTitleBar

/**
 * 可以使用沉浸式的Fragment基类
 *
 * @author geyifeng
 * @date 2017/4/7
 */
abstract class BaseImmersionFragment : Fragment() {

    private lateinit var binder: Unbinder
    protected var toolbar: Toolbar? = null
    protected var statusBarView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBeforeView(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder = ButterKnife.bind(this, view)
        statusBarView = view.findViewById(R.id.status_bar_view)
        toolbar = view.findViewById(R.id.toolbar)
        fitsLayoutOverlap()
        initData()
        initView()
        setListener()
        initImmersionBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbar = null
        statusBarView = null
        binder.unbind()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //旋转屏幕为什么要重新设置布局与状态栏重叠呢？因为旋转屏幕有可能使状态栏高度不一样，如果你是使用的静态方法修复的，所以要重新调用修复
        fitsLayoutOverlap()
    }

    protected open fun initDataBeforeView(savedInstanceState: Bundle?) {}

    /**
     * Gets layout id.
     *
     * @return the layout id
     */
    protected abstract val layoutId: Int

    protected open fun initImmersionBar() {}

    /**
     * 初始化数据
     */
    protected open fun initData() {}

    /**
     * view与数据绑定
     */
    protected open fun initView() {}

    /**
     * 设置监听
     */
    protected open fun setListener() {}
    private fun fitsLayoutOverlap() {
        if (statusBarView != null) {
            setStatusBarView(statusBarView!!)
        } else {
            toolbar?.let { setTitleBar(it) }
        }
    }
}