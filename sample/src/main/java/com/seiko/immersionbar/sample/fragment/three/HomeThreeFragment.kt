package com.seiko.immersionbar.sample.fragment.three

import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.adapter.OneAdapter
import com.seiko.immersionbar.sample.fragment.BaseFragment
import com.seiko.immersionbar.sample.utils.GlideImageLoader
import com.seiko.immersionbar.sample.utils.Utils
import com.seiko.immersionbar.statusBarHeight
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.youth.banner.Banner
import java.util.*
import kotlin.math.abs

/**
 * @author geyifeng
 * @date 2017/5/12
 */
class HomeThreeFragment : BaseFragment() {

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.rv)
    lateinit var mRv: RecyclerView

    @BindView(R.id.refreshLayout)
    lateinit var refreshLayout: TwinklingRefreshLayout

    private lateinit var mOneAdapter: OneAdapter
    private val mItemList: MutableList<String> = ArrayList()
    private var mImages: List<String?> = ArrayList()
    private var bannerHeight = 0

    override val layoutId: Int = R.layout.fragment_one_home

    override fun initData() {
        for (i in 1..20) {
            mItemList.add("item$i")
        }
        mImages = Utils.getPics()
    }

    override fun initView() {
        refreshLayout.setEnableLoadmore(false)
        val linearLayoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)
        mRv.layoutManager = linearLayoutManager
        mOneAdapter = OneAdapter()
        mOneAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        mRv.adapter = mOneAdapter
        addHeaderView()
        mOneAdapter.setPreLoadNumber(1)
        mOneAdapter.setNewData(mItemList)
    }

    private fun addHeaderView() {
        if (mImages.isNotEmpty()) {
            val headView = LayoutInflater.from(requireActivity()).inflate(R.layout.item_banner, mRv.parent as ViewGroup, false)
            val banner: Banner = headView.findViewById(R.id.banner)
            banner.setImages(mImages)
                .setImageLoader(GlideImageLoader())
                .setDelayTime(5000)
                .start()
            mOneAdapter.addHeaderView(headView)
            val bannerParams = banner.layoutParams
            val titleBarParams = mToolbar.layoutParams
            bannerHeight = bannerParams.height - titleBarParams.height - statusBarHeight
        }
    }

    override fun setListener() {
        mRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var totalDy = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalDy += dy
                if (totalDy <= bannerHeight) {
                    val alpha = totalDy.toFloat() / bannerHeight
                    mToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                        , ContextCompat.getColor(requireActivity(), R.color.colorPrimary), alpha))
                } else {
                    mToolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                        , ContextCompat.getColor(requireActivity(), R.color.colorPrimary), 1f))
                }
            }
        })
        mOneAdapter.setOnLoadMoreListener({
            Handler().postDelayed({
                mOneAdapter.addData(addData())
                if (mItemList.size == 100) {
                    mOneAdapter.loadMoreEnd()
                } else {
                    mOneAdapter.loadMoreComplete()
                }
            }, 2000)
        }, mRv)
        refreshLayout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
                Handler().postDelayed({
                    mItemList.clear()
                    mItemList.addAll(newData())
                    mOneAdapter.setNewData(mItemList)
                    refreshLayout.finishRefreshing()
                    mToolbar.visibility = View.VISIBLE
                }, 2000)
            }

            override fun onPullingDown(refreshLayout: TwinklingRefreshLayout, fraction: Float) {
                mToolbar.visibility = View.GONE
            }

            override fun onPullDownReleasing(refreshLayout: TwinklingRefreshLayout, fraction: Float) {
                if (abs(fraction - 1.0f) > 0) {
                    mToolbar.visibility = View.VISIBLE
                } else {
                    mToolbar.visibility = View.GONE
                }
            }
        })
    }

    private fun addData(): List<String> {
        val data: MutableList<String> = ArrayList()
        for (i in mItemList.size + 1..mItemList.size + 20) {
            data.add("item$i")
        }
        return data
    }

    private fun newData(): List<String> {
        val data: MutableList<String> = ArrayList()
        for (i in 1..20) {
            data.add("item$i")
        }
        return data
    }
}