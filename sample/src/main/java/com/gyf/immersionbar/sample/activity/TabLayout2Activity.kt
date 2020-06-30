package com.gyf.immersionbar.sample.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.gyf.immersionbar.*
import com.gyf.immersionbar.sample.AppManager
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.adapter.TabAdapter
import com.gyf.immersionbar.sample.utils.Utils
import me.imid.swipebacklayout.lib.app.SwipeBackActivity
import java.util.*
import kotlin.math.abs

/**
 * @author geyifeng
 * @date 2017/5/30
 */
class TabLayout2Activity : SwipeBackActivity() {
    private var mData: MutableList<String>? = null
    private var mAdapter: TabAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getInstance().addActivity(this)
        setContentView(R.layout.activity_tab_layout_two)
        initData(1)
        initView()
        setStatusBarView(findViewById(R.id.view))
        immersionBar {
            navigationBarColor(Color.WHITE)
            autoDarkModeEnable(true, 0.2f)
        }
        Glide.with(this).asBitmap().load(Utils.getPic())
            .apply(RequestOptions().placeholder(R.mipmap.test))
            .into((findViewById(R.id.mIv) as ImageView))
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
        setTitleBar(findViewById(R.id.toolbar))
        val appBarLayout = findViewById(R.id.app_bar) as AppBarLayout
        val collapsingToolbarLayout = findViewById(R.id.toolbar_layout) as CollapsingToolbarLayout
        collapsingToolbarLayout.post {
            val offHeight: Int = collapsingToolbarLayout.height - statusBarHeight
            appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { _: AppBarLayout?, i: Int ->
                immersionBar {
                    statusBarDarkFont(abs(i) >= offHeight, 0.2f)
                }
            })
        }


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