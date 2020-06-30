package com.gyf.immersionbar.sample.activity

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.FragmentTransaction
import butterknife.BindView
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.fragment.four.CategoryFourFragment
import com.gyf.immersionbar.sample.fragment.four.HomeFourFragment
import com.gyf.immersionbar.sample.fragment.four.MineFourFragment
import com.gyf.immersionbar.sample.fragment.four.ServiceFourFragment
import com.gyf.immersionbar.sample.utils.getResColor

/**
 * @author geyifeng
 * @date 2017/7/20
 */
class FragmentFourActivity : BaseActivity(), View.OnClickListener {

    @BindView(R.id.content)
    lateinit var content: FrameLayout

    @BindView(R.id.ll_home)
    lateinit var llHome: LinearLayout

    @BindView(R.id.ll_category)
    lateinit var llCategory: LinearLayout

    @BindView(R.id.ll_service)
    lateinit var llService: LinearLayout

    @BindView(R.id.ll_mine)
    lateinit var llMine: LinearLayout

    private var homeFourFragment: HomeFourFragment? = null
    private var categoryFourFragment: CategoryFourFragment? = null
    private var serviceFourFragment: ServiceFourFragment? = null
    private var mineFourFragment: MineFourFragment? = null
    override val layoutId: Int = R.layout.activity_fragment_two

    override fun initView() {
        selectedFragment(0)
        tabSelected(llHome)
    }

    override fun setListener() {
        llHome.setOnClickListener(this)
        llCategory.setOnClickListener(this)
        llService.setOnClickListener(this)
        llMine.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_home -> {
                selectedFragment(0)
                tabSelected(llHome)
                immersionBar {
                    keyboardEnable(false)
                    statusBarDarkFont(false)
                    navigationBarColor(getResColor(R.color.colorPrimary))
                }
            }
            R.id.ll_category -> {
                selectedFragment(1)
                tabSelected(llCategory)
                immersionBar {
                    keyboardEnable(true)
                    statusBarDarkFont(true, 0.2f)
                    navigationBarColor(getResColor(R.color.btn1))
                }
            }
            R.id.ll_service -> {
                selectedFragment(2)
                tabSelected(llService)
                immersionBar {
                    keyboardEnable(false)
                    statusBarDarkFont(true, 0.2f)
                    navigationBarColor(getResColor(R.color.btn2))
                }
            }
            R.id.ll_mine -> {
                selectedFragment(3)
                tabSelected(llMine)
                immersionBar {
                    keyboardEnable(true)
                    statusBarDarkFont(false)
                    navigationBarColor(getResColor(R.color.btn7))
                }
            }
            else -> {
            }
        }
    }

    private fun selectedFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        when (position) {
            0 -> if (homeFourFragment == null) {
                homeFourFragment = HomeFourFragment()
                transaction.add(R.id.content, homeFourFragment!!)
            } else {
                transaction.show(homeFourFragment!!)
            }
            1 -> if (categoryFourFragment == null) {
                categoryFourFragment = CategoryFourFragment()
                transaction.add(R.id.content, categoryFourFragment!!)
            } else {
                transaction.show(categoryFourFragment!!)
            }
            2 -> if (serviceFourFragment == null) {
                serviceFourFragment = ServiceFourFragment()
                transaction.add(R.id.content, serviceFourFragment!!)
            } else {
                transaction.show(serviceFourFragment!!)
            }
            3 -> if (mineFourFragment == null) {
                mineFourFragment = MineFourFragment()
                transaction.add(R.id.content, mineFourFragment!!)
            } else {
                transaction.show(mineFourFragment!!)
            }
            else -> {
            }
        }
        transaction.commit()
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        if (homeFourFragment != null) {
            transaction.hide(homeFourFragment!!)
        }
        if (categoryFourFragment != null) {
            transaction.hide(categoryFourFragment!!)
        }
        if (serviceFourFragment != null) {
            transaction.hide(serviceFourFragment!!)
        }
        if (mineFourFragment != null) {
            transaction.hide(mineFourFragment!!)
        }
    }

    private fun tabSelected(linearLayout: LinearLayout?) {
        llHome.isSelected = false
        llCategory.isSelected = false
        llService.isSelected = false
        llMine.isSelected = false
        linearLayout!!.isSelected = true
    }
}