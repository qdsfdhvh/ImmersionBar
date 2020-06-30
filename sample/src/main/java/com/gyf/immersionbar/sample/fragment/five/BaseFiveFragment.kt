package com.gyf.immersionbar.sample.fragment.five

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.setStatusBarView
import com.gyf.immersionbar.setTitleBar
import me.yokeyword.fragmentation.SupportFragment

/**
 * 基于Fragmentation框架实现沉浸式，如果要在Fragment实现沉浸式，请在onSupportVisible实现沉浸式，
 * 至于解决布局重叠问题，建议使用readme里第四种或者第五种，参考onViewCreated方法
 *
 * @author geyifeng
 * @date 2017/8/12
 */
abstract class BaseFiveFragment : SupportFragment() {

    private var unbinder: Unbinder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        unbinder = ButterKnife.bind(this, view)
        view.findViewById<View?>(setTitleBar())?.let {
            setTitleBar(it)
        }
        view.findViewById<View?>(setStatusBarView())?.let {
            setStatusBarView(it)
        }
        initData()
        initView()
        setListener()
    }

    protected fun setTitleBar(): Int {
        return R.id.toolbar
    }

    protected fun setStatusBarView(): Int {
        return 0
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        //请在onSupportVisible实现沉浸式
        if (isImmersionBarEnabled) {
            initImmersionBar()
        }
    }

    protected open fun initImmersionBar() {}
    private val isImmersionBarEnabled: Boolean = true

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }

    /**
     * Gets layout id.
     *
     * @return the layout id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化数据
     */
    protected fun initData() {}

    /**
     * view与数据绑定
     */
    protected open fun initView() {}

    /**
     * 设置监听
     */
    protected open fun setListener() {}
}