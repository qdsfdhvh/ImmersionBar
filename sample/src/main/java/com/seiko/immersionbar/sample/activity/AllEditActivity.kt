package com.seiko.immersionbar.sample.activity

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.adapter.EditAdapter
import com.seiko.immersionbar.setTitleBar
import java.util.*

/**
 * @author geyifeng
 */
class AllEditActivity : BaseActivity() {

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    private val mData = ArrayList<String>()

    override val layoutId: Int = R.layout.activity_all_edit

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(findViewById(R.id.toolbar))
        immersionBar {
            keyboardEnable(true)
        }
    }

    override fun initData() {
        super.initData()
        for (i in 0..19) {
            mData.add(i.toString())
        }
    }

    override fun initView() {
        super.initView()
        val adapter = EditAdapter()
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_edit_header, recyclerView, false))
        adapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_edit_footer, recyclerView, false))
        adapter.setNewData(mData)
        recyclerView.adapter = adapter
    }
}