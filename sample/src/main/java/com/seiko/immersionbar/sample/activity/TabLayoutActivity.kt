package com.seiko.immersionbar.sample.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.AppManager
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.adapter.TabAdapter
import com.seiko.immersionbar.sample.utils.getResColor
import com.seiko.immersionbar.setStatusBarView
import me.imid.swipebacklayout.lib.app.SwipeBackActivity
import java.util.*

/**
 * @author geyifeng
 * @date 2017/7/4
 */
class TabLayoutActivity : SwipeBackActivity() {

    private var mData: MutableList<String>? = null
    private var mAdapter: TabAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        setContentView(R.layout.activity_tab_layout)
        initData(1)
        initView()
        setStatusBarView(findViewById(R.id.view))
        immersionBar {
            navigationBarColor(getResColor(R.color.cool_green_normal))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getInstance().removeActivity(this)
    }

    private fun initData(pager: Int) {
        mData = ArrayList()
        for (i in 1..49) {
            mData!!.add("pager" + pager + " 第" + i + "个item")
        }
    }

    private fun initView() {
        //设置ToolBar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = ""
        //setSupportActionBar(toolbar);//替换系统的actionBar

        //设置TabLayout
        val tabLayout = findViewById(R.id.tabLayout) as TabLayout
        for (i in 1..6) {
            tabLayout.addTab(tabLayout.newTab().setText("TAB$i"))
        }
        //TabLayout的切换监听
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //切换的时候更新RecyclerView
                initData(tab.position + 1)
                mAdapter!!.setNewData(mData)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        //设置RecycleView
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        mAdapter = TabAdapter()
        recyclerView.adapter = mAdapter
        mAdapter!!.setNewData(mData)
    }
}