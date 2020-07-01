package com.seiko.immersionbar.sample.activity

import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import butterknife.BindView
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.fragment.three.CategoryThreeFragment
import com.seiko.immersionbar.sample.fragment.three.HomeThreeFragment
import com.seiko.immersionbar.sample.fragment.three.MineThreeFragment
import com.seiko.immersionbar.sample.fragment.three.ServiceThreeFragment
import com.seiko.immersionbar.sample.utils.getResColor
import com.seiko.immersionbar.sample.view.CustomViewPager
import java.util.*

/**
 * @author geyifeng
 * @date 2017/5/8
 */
class FragmentThreeActivity : BaseActivity(), View.OnClickListener, OnPageChangeListener {

    @BindView(R.id.viewPager)
    lateinit var viewPager: CustomViewPager

    @BindView(R.id.ll_home)
    lateinit var llHome: LinearLayout

    @BindView(R.id.ll_category)
    lateinit var llCategory: LinearLayout

    @BindView(R.id.ll_service)
    lateinit var llService: LinearLayout

    @BindView(R.id.ll_mine)
    lateinit var llMine: LinearLayout

    private var mFragments: ArrayList<Fragment> = ArrayList()

    override val layoutId: Int = R.layout.activity_fragment_one

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar {
            navigationBarColor(getResColor(R.color.colorPrimary))
        }
    }

    override fun initData() {
        mFragments = ArrayList()
        val homeThreeFragment = HomeThreeFragment()
        val categoryThreeFragment = CategoryThreeFragment()
        val serviceThreeFragment = ServiceThreeFragment()
        val mineThreeFragment = MineThreeFragment()
        mFragments.add(homeThreeFragment)
        mFragments.add(categoryThreeFragment)
        mFragments.add(serviceThreeFragment)
        mFragments.add(mineThreeFragment)
    }

    override fun initView() {
        viewPager.adapter = MyAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 3
        llHome.isSelected = true
    }

    override fun setListener() {
        llHome.setOnClickListener(this)
        llCategory.setOnClickListener(this)
        llService.setOnClickListener(this)
        llMine.setOnClickListener(this)
        viewPager.addOnPageChangeListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_home -> {
                viewPager.currentItem = 0
                tabSelected(llHome)
            }
            R.id.ll_category -> {
                viewPager.currentItem = 1
                tabSelected(llCategory)
            }
            R.id.ll_service -> {
                viewPager.currentItem = 2
                tabSelected(llService)
            }
            R.id.ll_mine -> {
                viewPager.currentItem = 3
                tabSelected(llMine)
            }
            else -> {
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                tabSelected(llHome)
                immersionBar {
                    keyboardEnable(false)
                    statusBarDarkFont(false)
                    navigationBarColor(getResColor(R.color.colorPrimary))
                }
            }
            1 -> {
                tabSelected(llCategory)
                immersionBar {
                    keyboardEnable(false)
                    statusBarDarkFont(true, 0.2f)
                    navigationBarColor(getResColor(R.color.btn3))
                }
            }
            2 -> {
                tabSelected(llService)
                immersionBar {
                    keyboardEnable(false)
                    statusBarDarkFont(false)
                    navigationBarColor(getResColor(R.color.btn13))
                }
            }
            3 -> {
                tabSelected(llMine)
                immersionBar {
                    keyboardEnable(true)
                    statusBarDarkFont(true)
                    navigationBarColor(getResColor(R.color.btn1))
                }
            }
            else -> {}
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}
    private fun tabSelected(linearLayout: LinearLayout?) {
        llHome.isSelected = false
        llCategory.isSelected = false
        llService.isSelected = false
        llMine.isSelected = false
        linearLayout!!.isSelected = true
    }

    private inner class MyAdapter (
        fm: FragmentManager
    ) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }
    }
}