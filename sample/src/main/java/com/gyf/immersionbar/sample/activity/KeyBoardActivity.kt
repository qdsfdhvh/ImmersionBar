package com.gyf.immersionbar.sample.activity

import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.setTitleBar
import java.util.*

/**
 * @author geyifeng
 * @date 2017/5/8
 */
class KeyBoardActivity : BaseActivity() {

    @BindView(R.id.line)
    lateinit var layout: LinearLayout

    @BindView(R.id.list_view)
    lateinit var listView: ListView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    private lateinit var mapList: MutableList<Map<String, Any>>

    override val layoutId: Int = R.layout.activity_key_board

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(toolbar)
        immersionBar {
            keyboardEnable(true)
        }
    }

    override fun initData() {
        mapList = ArrayList()
        for (i in 1..20) {
            val map: MutableMap<String, Any> = HashMap()
            map["desc"] = "我是假数据$i"
            mapList.add(map)
        }
    }

    override fun initView() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        listView.adapter = SimpleAdapter(this, mapList,
            R.layout.item_simple, arrayOf("desc"), intArrayOf(R.id.text))
    }

    override fun setListener() {
        //toolbar返回按钮监听
        toolbar.setNavigationOnClickListener { finish() }
    }
}