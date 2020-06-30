package com.gyf.immersionbar.sample.activity

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.FrameLayout
import android.widget.PopupWindow
import butterknife.OnClick
import com.gyf.immersionbar.immersionBar
import com.gyf.immersionbar.navigationBarHeight
import com.gyf.immersionbar.navigationBarWidth
import com.gyf.immersionbar.sample.R
import com.gyf.immersionbar.sample.utils.Utils
import com.gyf.immersionbar.setTitleBar
import com.gyf.immersionbar.util.isEMUI3x

/**
 * 结合popupWindow使用
 * The type Popup activity.
 *
 * @author geyifeng
 */
class PopupActivity : BaseActivity() {

    private var mPopupView: View? = null
    private var mPopupWindow: PopupWindow? = null
    private var mCurPosition = 0

    override val layoutId: Int = R.layout.activity_popup

    override fun initImmersionBar() {
        super.initImmersionBar()
        setTitleBar(findViewById(R.id.toolbar))
        // 华为手机能手动隐藏导航栏，所以也要做些适配
        // setOnNavigationBarListener({ show -> updatePopupView() })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //适配横竖屏切换
        updatePopupWindow()
        updatePopupView()
    }

    /**
     * On view click.
     *
     * @param view the view
     */
    @OnClick(R.id.btn_full, R.id.btn_top, R.id.btn_bottom, R.id.btn_left, R.id.btn_right)
    fun onViewClick(view: View) {
        val widthAndHeight = Utils.getWidthAndHeight(window)
        mCurPosition = view.id
        when (view.id) {
            R.id.btn_full -> showPopup(Gravity.NO_GRAVITY, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, R.style.RightAnimation)
            R.id.btn_top -> showPopup(Gravity.TOP, ViewGroup.LayoutParams.MATCH_PARENT, widthAndHeight[1] / 2, R.style.TopAnimation)
            R.id.btn_bottom -> showPopup(Gravity.BOTTOM, ViewGroup.LayoutParams.MATCH_PARENT, widthAndHeight[1] / 2, R.style.BottomAnimation)
            R.id.btn_left -> showPopup(Gravity.START, widthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT, R.style.LeftAnimation)
            R.id.btn_right -> showPopup(Gravity.END, widthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT, R.style.RightAnimation)
            else -> {
            }
        }
    }

    /**
     * 弹出popupWindow
     * Show popup.
     *
     * @param gravity        the gravity
     * @param width          the width
     * @param height         the height
     * @param animationStyle the animation style
     */
    private fun showPopup(gravity: Int, width: Int, height: Int, animationStyle: Int) {
        mPopupView = LayoutInflater.from(this).inflate(R.layout.popup_demo, null)
        mPopupWindow = PopupWindow(mPopupView, width, height)
        //以下属性响应空白处消失和实体按键返回消失popup
        mPopupWindow!!.isOutsideTouchable = true
        mPopupWindow!!.isFocusable = true
        mPopupWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //沉浸式模式下，以下两个属性并不起作用
        mPopupWindow!!.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
        mPopupWindow!!.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        //重点，此方法可以让布局延伸到状态栏和导航栏
        mPopupWindow!!.isClippingEnabled = false
        //设置动画
        mPopupWindow!!.animationStyle = animationStyle
        //弹出
        mPopupWindow!!.showAtLocation(window.decorView, gravity, 0, 0)
        //弹出后背景alpha值
        backgroundAlpha(0.5f)
        //消失后恢复背景alpha值
        mPopupWindow!!.setOnDismissListener { backgroundAlpha(1f) }
        //适配弹出popup后布局被状态栏和导航栏遮挡问题
        updatePopupView()
    }

    /**
     * 调整popupWindow位置以及宽高，不然横竖屏切换会导致显示位置改变以及宽高变化
     * Update popup window.
     */
    private fun updatePopupWindow() {
        val widthAndHeight = Utils.getWidthAndHeight(window)
        when (mCurPosition) {
            R.id.btn_top, R.id.btn_bottom -> mPopupWindow!!.update(0, 0, ViewGroup.LayoutParams.MATCH_PARENT, widthAndHeight[1] / 2)
            R.id.btn_left, R.id.btn_right -> mPopupWindow!!.update(0, 0, widthAndHeight[0] / 2, ViewGroup.LayoutParams.MATCH_PARENT)
            else -> {
            }
        }
    }

    /**
     * 调整popupWindow里view的Margins值来适配布局被导航栏遮挡问题，因为要适配横竖屏切换，所以代码有点多
     * Update popup view.
     */
    private fun updatePopupView() {
        val navigationBarHeight = navigationBarHeight
        val navigationBarWidth = navigationBarWidth
        if (mPopupView != null) {
            setTitleBar(mPopupView!!.findViewById(R.id.toolbar))
            val rlContent = mPopupView!!.findViewById<View>(R.id.rlContent)
            mPopupView!!.post {
                val isPortrait: Boolean
                val isLandscapeLeft: Boolean
                val rotation = windowManager.defaultDisplay.rotation
                if (rotation == Surface.ROTATION_90) {
                    isPortrait = false
                    isLandscapeLeft = true
                } else if (rotation == Surface.ROTATION_270) {
                    isPortrait = false
                    isLandscapeLeft = false
                } else {
                    isPortrait = true
                    isLandscapeLeft = false
                }
                val layoutParams = rlContent.layoutParams as FrameLayout.LayoutParams
                when (mCurPosition) {
                    R.id.btn_full, R.id.btn_top, R.id.btn_bottom -> if (isPortrait) {
                        layoutParams.setMargins(0, 0, 0, navigationBarHeight)
                    } else {
                        if (isLandscapeLeft) {
                            layoutParams.setMargins(0, 0, navigationBarWidth, 0)
                        } else {
                            if (isEMUI3x()) {
                                layoutParams.setMargins(0, 0, navigationBarWidth, 0)
                            } else {
                                layoutParams.setMargins(navigationBarWidth, 0, 0, 0)
                            }
                        }
                    }
                    R.id.btn_left -> if (isPortrait) {
                        layoutParams.setMargins(0, 0, 0, navigationBarHeight)
                    } else {
                        if (isLandscapeLeft) {
                            layoutParams.setMargins(0, 0, 0, 0)
                        } else {
                            if (isEMUI3x()) {
                                layoutParams.setMargins(0, 0, navigationBarWidth, 0)
                            } else {
                                layoutParams.setMargins(navigationBarWidth, 0, 0, 0)
                            }
                        }
                    }
                    R.id.btn_right -> if (isPortrait) {
                        layoutParams.setMargins(0, 0, 0, navigationBarHeight)
                    } else {
                        if (isLandscapeLeft) {
                            layoutParams.setMargins(0, 0, navigationBarWidth, 0)
                        } else {
                            if (isEMUI3x()) {
                                layoutParams.setMargins(0, 0, navigationBarWidth, 0)
                            } else {
                                layoutParams.setMargins(0, 0, 0, 0)
                            }
                        }
                    }
                    else -> {
                    }
                }
                rlContent.layoutParams = layoutParams
            }
        }
    }

    /**
     * 设置弹出popup，背景alpha值
     *
     * @param bgAlpha 0f - 1f
     */
    fun backgroundAlpha(bgAlpha: Float) {
        val lp = window.attributes
        lp.alpha = bgAlpha
        window.attributes = lp
    }
}