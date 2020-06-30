package com.gyf.immersionbar

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.gyf.immersionbar.annotation.BarHide
import com.gyf.immersionbar.util.*

class ImmersionBar(
    private val activity: FragmentActivity,
    builder: BarConfig.Builder.() -> Unit,
    internal val window: Window = activity.window
) : JavaFun() {

    /**
     * Bar配置
     */
    internal var barConfig = BarConfig.Builder().apply(builder).build()

    /**
     * Bar尺寸
     */
    internal lateinit var barSize: BarSize

    internal val decorView = window.decorView as ViewGroup
    private val contentView = decorView.getContentView()

    /**
     * 软键盘适配
     */
//    private var keyboardTempEnable: Boolean = false
    private var fitsKeyboard: FitsKeyboard? = null

    /**
     * 状态
     */
    private var isCreated = false
    private var isResumed = false


    internal var paddingLeft = 0
    internal var paddingTop = 0
    internal var paddingRight = 0
    internal var paddingBottom = 0

    /**
     * 导航栏系统默认颜色
     */
    @ColorInt
    private var navigationBarColorDefault: Int = Color.BLACK

    /**
     * ActionBar是否是在LOLLIPOP下设备使用
     */
    private var isActionBarBelowLOLLIPOP: Boolean = false

    fun onCreate() {
        // 更新Bar参数
        updateBarParams()
        // 修正界面显示
        fitsWindows()
        // 初始化完成
        isCreated = true
    }

    fun onResume() {
        if (isEMUI3x() && barConfig.navigationBarWithEMUI3Enable) {
            onCreate()
        }
        // 适配软键盘与底部输入冲突问题
        fitsKeyboard()
        // 设置沉浸式
        setBar()
        // 变色View
        transformView()

        FitsKeyboardManager.add(fitsKeyboard)
        isResumed = true
    }

    fun onPause() {
        FitsKeyboardManager.pop(fitsKeyboard)
        isResumed = false
    }

    fun onDestroy() {
        if (isResumed) {
            onPause()
        }
//        cancelListener()
//        if (isDialog) {
//            parent?.let {
//                it.barConfig.keyboardEnable = barConfig.keyboardEnable
//                if (it.barConfig.barHideCode != BarHide.FLAG_SHOW_BAR) {
//                    it.setBar()
//                }
//            }
//        }
        isCreated = false
    }

    /**
     * 更新Bar的参数
     */
    private fun updateBarParams() {
        // 尝试自动调整深色亮色模式参数
        barConfig.adjustDarkModeParams()
        // 更新BarSize
        updateBarConfig()
//        parent?.let {
//            // 如果在Fragment中使用，让Activity同步Fragment的BarParams参数
//            if (isFragment) {
//                it.barConfig = barConfig
//            }
//            // 如果dialog里设置了keyboardEnable为true，
//            // 则Activity中所设置的keyboardEnable为false
//            if (isDialog) {
//                if (it.keyboardTempEnable) {
//                    it.barConfig.keyboardEnable = false
//                }
//            }
//        }
    }

    /**
     * 更新BarSize
     */
    private fun updateBarConfig() {
        barSize = BarSize.create(activity)
    }

    /**
     * 初始化状态栏和导航栏
     */
    @SuppressLint("NewApi")
    internal fun setBar() {
        // 防止系统栏隐藏时内容区域大小发生变化
        var uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        if (isLOLLIPOPLater && !isEMUI3x()) {
            // 适配刘海屏 安卓P 以上
            if (isPLater && !isCreated) {
                window.fitsNotchScreen()
            }
            // 初始化5.0以上，包含5.0
            uiFlags = initBarAboveLOLLIPOP(uiFlags)
            // android 6.0以上设置状态栏字体为暗色
            if (isMLater && barConfig.statusBarDarkFont) {
                uiFlags = uiFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            // android 8.0以上设置导航栏图标为暗色
            if (isOLater && barConfig.navigationBarDarkIcon) {
                uiFlags = uiFlags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        } else {
            // 初始化5.0以下，4.4以上沉浸式
            initBarBelowLOLLIPOP()
        }
        // 隐藏状态栏或者导航栏
        uiFlags = hideBarFlag(barConfig.barHideCode, uiFlags)
        decorView.systemUiVisibility = uiFlags
        setSpecialBarDarkMode()
        // TODO 导航栏显示隐藏监听，目前只支持带有导航栏的华为和小米手机
        // NavigationBarObserver
    }

    /**
     * 初始化android 5.0以上状态栏和导航栏
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun initBarAboveLOLLIPOP(flag: Int): Int {
        var uiFlags = flag
        if (!isCreated) {
            navigationBarColorDefault = window.navigationBarColor
        }
        // Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态栏遮住。
        uiFlags = uiFlags or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (barConfig.fullScreen && barConfig.navigationBarEnable) {
            // Activity全屏显示，但导航栏不会被隐藏覆盖，导航栏依然可见，Activity底部布局部分会被导航栏遮住。
            uiFlags = uiFlags or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }

        // 去除'透明状态栏'与'透明导航栏'标记
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        if (barSize.hasNavigationBar) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        // 需要设置这个才能设置状态栏和导航栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // 自定义状态栏颜色
        window.statusBarColor = if (barConfig.statusBarColorEnabled) {
            barConfig.createStatusColor()
        } else {
            barConfig.createTransparentStatusColor()
        }
        // 自定义导航栏颜色
        window.navigationBarColor = if (barConfig.navigationBarEnable) {
            barConfig.createNavigationColor()
        } else {
            navigationBarColorDefault
        }
        return uiFlags
    }

    /**
     * 初始化android 4.4和emui3.1状态栏和导航栏
     */
    private fun initBarBelowLOLLIPOP() {
        // 透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //创建一个假的状态栏
        setupStatusBarView(decorView, barConfig, barSize)
        // 判断是否存在导航栏，是否禁止设置导航栏
        if (barSize.hasNavigationBar || isEMUI3x()) {
            if (barConfig.navigationBarEnable && barConfig.navigationBarWithKitkatEnable) {
                // 透明导航栏，设置这个，如果有导航栏，底部布局会被导航栏遮住
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            }
            // 创建一个假的导航栏
            setupNavigationBarView(decorView, barConfig, barSize)
        }
    }

    private fun setSpecialBarDarkMode() {
        if (isMIUI6()) {
            // 修改miui状态栏字体颜色
            window.setStatusBarDarkWithMIUI(barConfig.statusBarDarkFont)
            // 修改miui导航栏图标为黑色
            if (barConfig.navigationBarEnable) {
                window.setNavigationBarDarkWithMIUI(barConfig.navigationBarDarkIcon)
            }
        } else if (isFlymeOS4()) {
            if (barConfig.flymeOSStatusBarFontColor != 0) {
                activity.setStatusBarDarkIcon(barConfig.flymeOSStatusBarFontColor)
            } else {
                activity.setStatusBarDarkIcon(barConfig.statusBarDarkFont)
            }
        }
    }

    /**
     * 修正界面显示
     */
    private fun fitsWindows() {
        if (isLOLLIPOPLater && !isEMUI3x()) {
            // android 5.0以上解决状态栏和布局重叠问题
            fitsWindowsAboveLOLLIPOP()
        } else {
            // android 5.0以下解决状态栏和布局重叠问题
            fitsWindowsBelowLOLLIPOP()
        }
        // 适配状态栏与布局重叠问题
//        fitsLayoutOverlap()
    }

    /**
     * android 5.0以上解决状态栏和布局重叠问题
     */
    private fun fitsWindowsAboveLOLLIPOP() {
//        updateBarConfig()
        if (checkFitsSystemWindows(contentView)) {
            setPadding(0, 0, 0, 0)
            return
        }

        var top = 0
//        if (barConfig.fits && barConfig.fitsStatusBarType == FitsFlag.SYSTEM_WINDOWS) {
//            top = barSize.statusBarHeight
//        }
        if (barConfig.isSupportActionBar) {
            top = barSize.statusBarHeight + barSize.actionBarHeight
        }
        setPadding(0, top, 0, 0)
    }

    /**
     * android 5.0以下解决状态栏和布局重叠问题
     */
    private fun fitsWindowsBelowLOLLIPOP() {
        if (barConfig.isSupportActionBar) {
            isActionBarBelowLOLLIPOP = true
            contentView.post { postFitsWindowsBelowLOLLIPOP() }
        } else {
            isActionBarBelowLOLLIPOP = false
            postFitsWindowsBelowLOLLIPOP()
        }
    }

    private fun postFitsWindowsBelowLOLLIPOP() {
//        updateBarConfig()
        // 解决android4.4有导航栏的情况下，
        // activity底部被导航栏遮挡的问题和android 5.0以下解决状态栏和布局重叠问题
        fitsWindowsKITKAT()
        // 解决华为emui3.1或者3.0导航栏手动隐藏的问题
        if (isEMUI3x()) {
            fitsWindowsEMUI()
        }
    }

    /**
     * 解决android4.4有导航栏的情况下，
     * activity底部被导航栏遮挡的问题和android 5.0以下解决状态栏和布局重叠问题
     */
    private fun fitsWindowsKITKAT() {
        if (checkFitsSystemWindows(contentView)) {
            setPadding(0, 0, 0, 0)
            return
        }
        var top = 0; var right = 0; var bottom = 0
//        if (barConfig.fits && barConfig.fitsStatusBarType == FitsFlag.SYSTEM_WINDOWS) {
//            top = barSize.statusBarHeight
//        }
        if (barConfig.isSupportActionBar) {
            top = barSize.statusBarHeight + barSize.actionBarHeight
        }
        if (barSize.hasNavigationBar
            && barConfig.navigationBarEnable
            && barConfig.navigationBarWithKitkatEnable
        ) {
            if (!barConfig.fullScreen) {
                if (barSize.isNavigationAtBottom) {
                    bottom = barSize.navigationHeight
                } else {
                    right = barSize.navigationWidth
                }
            }
            if (barConfig.hideNavigationBar) {
                if (barSize.isNavigationAtBottom) {
                    bottom = 0
                } else {
                    right = 0
                }
            } else if (!barSize.isNavigationAtBottom) {
                right = barSize.navigationWidth
            }
        }
        setPadding(0, top, right, bottom)
    }

    /**
     * 注册emui3.x导航栏监听函数
     */
    private fun fitsWindowsEMUI() {
//        val navigationBarView = decorView.findViewWithTag<View>(IMMERSION_ID_NAVIGATION_BAR_VIEW)
//        if (barConfig.navigationBarEnable && barConfig.navigationBarWithKitkatEnable) {
//            if (navigationBarView != null) {
//
//            }
//        }
    }

    /**
     * 解决底部输入框与软键盘问题
     */
    private fun fitsKeyboard() {
        if (barConfig.keyboardEnable) {
            if (fitsKeyboard == null) {
                Log.d("ImmersionBar", "创建FitsKeyboard $this")
                fitsKeyboard = FitsKeyboard(this)
            }
        } else {
            fitsKeyboard?.let {
                FitsKeyboardManager.pop(it)
                fitsKeyboard = null
            }
        }
//            fitsKeyboard!!.enable(barConfig.keyboardMode)
//        } else {
//            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
//        }
//        if (barConfig.keyboardEnable) {
//            if (fitsKeyboard == null) {
//                fitsKeyboard = FitsKeyboard(this)
//            }
//            fitsKeyboard!!.enable(barConfig.keyboardMode)
//        } else {
//            fitsKeyboard?.disable()
//        }
    }

    /**
     * 变色view
     */
    private fun transformView() {
        var view: View; var pair: Pair<Int, Int>?
        barConfig.viewMap?.forEach { entry ->
            view = entry.key
            pair = entry.value
            view.setBackgroundColor(barConfig.createTransformColor(pair))
        }
    }

//    /**
//     * 取消 软键盘监听 和 emui3.x导航栏监听
//     */
//    private fun cancelListener() {
////        fitsKeyboard?.let {
////            it.cancel()
////            fitsKeyboard = null
////        }
//        // TODO EMUI3NavigationBarObserver removeOnNavigationBarListener
//    }

    private fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        contentView.setPadding(left, top, right, bottom)
        paddingLeft = left
        paddingTop = top
        paddingRight = right
        paddingBottom = bottom
    }

    /**
     * 更新配置
     */
    fun update(builder: BarConfig.Builder.() -> Unit) {
        barConfig = BarConfig.Builder(barConfig)
            .apply(builder)
            .build()
        // 如果已经初始化，重新加载
        if (isCreated) {
            onCreate()
            if (isResumed) {
                onResume()
            }
        }
    }
}

/**
 * 假状态栏TAG
 */
private const val IMMERSION_ID_STATUS_BAR_VIEW = "IMMERSION_ID_STATUS_BAR_VIEW"

/**
 * 创建一个可以自定义颜色的状态栏
 */
private fun setupStatusBarView(decorView: ViewGroup, barConfig: BarConfig, barSize: BarSize) {
    var statusBarView = decorView.findViewWithTag<View>(IMMERSION_ID_STATUS_BAR_VIEW)
    if (statusBarView == null) {
        statusBarView = View(decorView.context)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            barSize.statusBarHeight
        )
        params.gravity = Gravity.TOP
        statusBarView.layoutParams = params
        statusBarView.visibility = View.VISIBLE
        statusBarView.tag = IMMERSION_ID_STATUS_BAR_VIEW
        decorView.addView(statusBarView)
    }
    if (barConfig.statusBarColorEnabled) {
        statusBarView.setBackgroundColor(barConfig.createStatusColor())
    } else {
        statusBarView.setBackgroundColor(barConfig.createTransparentStatusColor())
    }
}

/**
 * 假导航栏TAG
 */
private const val IMMERSION_ID_NAVIGATION_BAR_VIEW = "IMMERSION_ID_NAVIGATION_BAR_VIEW"

/**
 * 创建一个假的导航栏
 */
private fun setupNavigationBarView(decorView: ViewGroup, barConfig: BarConfig, barSize: BarSize) {
    var navigationBarView = decorView.findViewWithTag<View>(IMMERSION_ID_NAVIGATION_BAR_VIEW)
    if (navigationBarView == null) {
        navigationBarView = View(decorView.context)
        navigationBarView.tag = IMMERSION_ID_NAVIGATION_BAR_VIEW
        decorView.addView(navigationBarView)
    }

    val params: FrameLayout.LayoutParams
    if (barSize.isNavigationAtBottom) {
        params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            barSize.navigationHeight
        )
        params.gravity = Gravity.BOTTOM
    } else {
        params = FrameLayout.LayoutParams(
            barSize.navigationWidth,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.gravity = Gravity.END
    }
    navigationBarView.layoutParams = params
    navigationBarView.setBackgroundColor(barConfig.createNavigationColor())

    if (barConfig.navigationBarEnable && barConfig.navigationBarWithKitkatEnable) {
        navigationBarView.visibility = View.VISIBLE
    } else {
        navigationBarView.visibility = View.GONE
    }
}

/**
 * 生成配置的状态栏颜色
 */
private fun BarConfig.createStatusColor(): Int {
    return ColorUtils.blendARGB(
        statusBarColor,
        statusBarColorTransform,
        statusBarAlpha
    )
}

/**
 * 生成配置的状态栏透明颜色
 */
private fun BarConfig.createTransparentStatusColor(): Int {
    return ColorUtils.blendARGB(
        statusBarColor,
        Color.TRANSPARENT,
        statusBarAlpha
    )
}

/**
 * 生成配置的导航栏颜色
 * PS: 高版本导航栏系统好像不能全透明
 */
private fun BarConfig.createNavigationColor(): Int {
    val color = ColorUtils.blendARGB(
        navigationBarColor,
        navigationBarColorTransform,
        navigationBarAlpha
    )
    return if (color == 0) 0x01000000 else color
}

/**
 * 生成变色View的颜色
 * @param pair 变化前后的颜色
 */
private fun BarConfig.createTransformColor(pair: Pair<Int, Int>?): Int {
    val colorBefore = pair?.first ?: statusBarColor
    val colorAfter = pair?.second ?: statusBarColorTransform
    return ColorUtils.blendARGB(
        colorBefore,
        colorAfter,
        viewAlpha ?: statusBarAlpha
    )
}