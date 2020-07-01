package com.seiko.immersionbar.sample.activity

import android.content.DialogInterface
import android.content.res.Configuration
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.seiko.immersionbar.destroyBar
import com.seiko.immersionbar.immersionBar
import com.seiko.immersionbar.sample.AppManager
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.fragment.dialog.*
import com.seiko.immersionbar.sample.utils.Utils
import com.seiko.immersionbar.sample.utils.getResColor
import com.seiko.immersionbar.setTitleBar

/**
 * @author geyifeng
 * @date 2017/7/31
 */
class DialogActivity : BaseActivity(), DialogInterface.OnDismissListener {

    @BindView(R.id.btn_full_fragment)
    lateinit var btnFullFragment: Button

    @BindView(R.id.btn_top_fragment)
    lateinit var btnTopFragment: Button

    @BindView(R.id.btn_bottom_fragment)
    lateinit var btnBottomFragment: Button

    @BindView(R.id.btn_left_fragment)
    lateinit var btnLeftFragment: Button

    @BindView(R.id.btn_right_fragment)
    lateinit var btnRightFragment: Button

    private var mAlertDialog: AlertDialog? = null
    private var mDialogWindow: Window? = null
    private var mId = 0

    override val layoutId: Int = R.layout.activity_dialog

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(findViewById(R.id.toolbar))
        immersionBar {
            keyboardEnable(true)
        }
    }

    override fun setListener() {
        btnFullFragment.setOnClickListener {
            val fullDialogFragment = FullDialogFragment()
            fullDialogFragment.show(supportFragmentManager, FullDialogFragment::class.java.simpleName)
        }
        btnTopFragment.setOnClickListener {
            val fullDialogFragment = TopDialogFragment()
            fullDialogFragment.show(supportFragmentManager, TopDialogFragment::class.java.simpleName)
        }
        btnBottomFragment.setOnClickListener {
            val fullDialogFragment = BottomDialogFragment()
            fullDialogFragment.show(supportFragmentManager, BottomDialogFragment::class.java.simpleName)
        }
        btnLeftFragment.setOnClickListener {
            val fullDialogFragment = LeftDialogFragment()
            fullDialogFragment.show(supportFragmentManager, LeftDialogFragment::class.java.simpleName)
        }
        btnRightFragment.setOnClickListener {
            val fullDialogFragment = RightDialogFragment()
            fullDialogFragment.show(supportFragmentManager, RightDialogFragment::class.java.simpleName)
        }
    }

    @OnClick(R.id.btn_full, R.id.btn_top, R.id.btn_bottom, R.id.btn_left, R.id.btn_right)
    fun onClick(view: View) {
        //弹出Dialog
        val builder = AlertDialog.Builder(this, R.style.MyDialog)
        mAlertDialog = builder.create()
        mAlertDialog!!.setOnDismissListener(this)
        mAlertDialog!!.show()
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
        val toolbar: Toolbar = dialogView.findViewById(R.id.toolbar)
        val iv = dialogView.findViewById<ImageView>(R.id.mIv)
        Glide.with(this).asBitmap().load(Utils.getPic())
            .apply(RequestOptions().placeholder(R.mipmap.test))
            .into(iv)
        mAlertDialog!!.setContentView(dialogView)
        mDialogWindow = mAlertDialog!!.window
        if (mDialogWindow != null) {
            //解决无法弹出输入法的问题
            mDialogWindow!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        }
        //计算屏幕宽高
        val widthAndHeight = Utils.getWidthAndHeight(window)
        if (mDialogWindow != null) {
            mId = view.id
            when (view.id) {
                R.id.btn_full -> {
                    mDialogWindow!!.setGravity(Gravity.TOP)
                    mDialogWindow!!.setWindowAnimations(R.style.RightAnimation)
                    mDialogWindow!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

                    setTitleBar(toolbar)
                    mAlertDialog!!.immersionBar(this) {
                        navigationBarColor(getResColor(R.color.btn3))
                        statusBarDarkFont(true)
                        keyboardEnable(true)
                    }
                }
                R.id.btn_top -> {
                    mDialogWindow!!.setGravity(Gravity.TOP)
                    mDialogWindow!!.setWindowAnimations(R.style.TopAnimation)
                    mDialogWindow!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, widthAndHeight[1] / 2)

                    setTitleBar(toolbar)
                    mAlertDialog!!.immersionBar(this) {
                        navigationBarWithKitkatEnable(
                            resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                        navigationBarColor(getResColor(R.color.btn4))
                        keyboardEnable(true)
                    }
                }
                R.id.btn_bottom -> {
                    mDialogWindow!!.setGravity(Gravity.BOTTOM)
                    mDialogWindow!!.setWindowAnimations(R.style.BottomAnimation)
                    mDialogWindow!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, widthAndHeight[1] / 2)

                    mAlertDialog!!.immersionBar(this) {
                        navigationBarColor(getResColor(R.color.cool_green_normal))
                    }
                }
                R.id.btn_left -> {
                    mDialogWindow!!.setGravity(Gravity.TOP or Gravity.START)
                    mDialogWindow!!.setWindowAnimations(R.style.LeftAnimation)
                    mDialogWindow!!.setLayout(widthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT)

                    setTitleBar(toolbar)
                    mAlertDialog!!.immersionBar(this) {
                        navigationBarWithKitkatEnable(
                            resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                        navigationBarColor(getResColor(R.color.btn11))
                        keyboardEnable(true)
                    }
                }
                R.id.btn_right -> {
                    mDialogWindow!!.setGravity(Gravity.TOP or Gravity.END)
                    mDialogWindow!!.setWindowAnimations(R.style.RightAnimation)
                    mDialogWindow!!.setLayout(widthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT)

                    setTitleBar(toolbar)
                    mAlertDialog!!.immersionBar(this) {
                        navigationBarColor(getResColor(R.color.btn8))
                        keyboardEnable(true)
                    }
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        AppManager.getInstance().hideSoftKeyBoard(this)
        mAlertDialog?.setOnDismissListener(null)
        mAlertDialog?.destroyBar(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val widthAndHeight = Utils.getWidthAndHeight(window)
        if (mAlertDialog != null) {
            when (mId) {
                R.id.btn_top -> mDialogWindow!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, widthAndHeight[1] / 2)
                R.id.btn_bottom -> mDialogWindow!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, widthAndHeight[1] / 2)
                R.id.btn_left -> mDialogWindow!!.setLayout(widthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT)
                R.id.btn_right -> mDialogWindow!!.setLayout(widthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
    }
}