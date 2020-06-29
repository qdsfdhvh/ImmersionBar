package com.gyf.immersionbar

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.gyf.immersionbar.annotation.BarHide
import com.gyf.immersionbar.util.IMMERSION_BOUNDARY_COLOR
import com.gyf.immersionbar.util.isEMUI3x

private const val DEFAULT_KEYBOARD_MODE = (
    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
    or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

data class BarConfig(
    // 状态栏颜色
    @ColorInt val statusBarColor: Int = Color.TRANSPARENT,
    // 状态栏变换后的颜色
    @ColorInt val statusBarColorTransform: Int = Color.BLACK,
    // 状态栏透明度
    @FloatRange(from = 0.0, to = 1.0) var statusBarAlpha: Float = 0.0f,
    @FloatRange(from = 0.0, to = 1.0) val statusBarTempAlpha: Float = 0.0f,

    // 导航栏颜色
    @ColorInt val navigationBarColor: Int = Color.BLACK,
    // 航栏变换后的颜色
    @ColorInt val navigationBarColorTransform: Int = Color.BLACK,
    // 导航栏透明度
    @FloatRange(from = 0.0, to = 1.0) var navigationBarAlpha: Float = 0.0f,
    @FloatRange(from = 0.0, to = 1.0) val navigationBarTempAlpha: Float = 0.0f,

    // 在有导航栏的情况下，是否全屏
    val fullScreen: Boolean = false,

    // 自动根据StatusBar颜色调整深色模式
    val autoStatusBarDarkModeEnable: Boolean = false,
    @FloatRange(from = 0.0, to = 1.0) val autoStatusBarDarkModeAlpha: Float = 0.0f,
    // 自动根据NavigationBar颜色调整深色模式
    val autoNavigationBarDarkModeEnable: Boolean = false,
    @FloatRange(from = 0.0, to = 1.0) val autoNavigationBarDarkModeAlpha: Float = 0.0f,

    // 状态栏字体设为深色
    var statusBarDarkFont: Boolean = false,
    // 导航栏图标设为深色
    var navigationBarDarkIcon: Boolean = false,

    // flymeOS状态栏字体变色
    @ColorInt var flymeOSStatusBarFontColor: Int = 0,
    @ColorInt val flymeOSStatusBarFontTempColor: Int = 0,

    // 标题栏(StatusBar)与导航栏(NavigationBar)的显示状态
    @BarHide var barHideCode: Int = BarHide.FLAG_SHOW_BAR,
    // 是否隐藏导航栏
    val hideNavigationBar: Boolean = false,

//    // 解决标题栏与状态栏重叠问题
//    val fits: Boolean = false,
//    // 当前顶部布局和状态栏重叠是以哪种方式适配的
//    @FitsFlag val fitsStatusBarType: Int = FitsFlag.DEFAULT,

    // 整体界面背景色
    @ColorInt val contentColor: Int = Color.TRANSPARENT,
    // 整体界面变换后的背景色
    @ColorInt val contentColorTransform: Int = Color.BLACK,
    // 整体界面透明度
    @FloatRange(from = 0.0, to = 1.0) val contentAlpha: Float = 0.0f,

//    // 是否可以解决标题栏与状态栏重叠问题
//    val fitsLayoutOverlapEnable: Boolean = true,

//    // 解决标题栏与状态栏重叠问题
//    val statusBarView: View? = null,
//    // 解决标题栏与状态栏重叠问题v2
//    val titleBarView: View? = null,
    // 是否可以修改状态栏颜色
    val statusBarColorEnabled: Boolean = true,

    // 结合actionBar使用
    val isSupportActionBar: Boolean = false,

    // 解决软键盘与输入框冲突问题
    var keyboardEnable: Boolean = false,
    // 软键盘属性
    var keyboardMode: Int = DEFAULT_KEYBOARD_MODE,

    // 是否能修改导航栏颜色
    val navigationBarEnable: Boolean = true,
    // 是否能修改4.4手机以及华为emui3.1导航栏颜色
    val navigationBarWithKitkatEnable: Boolean = true,
    // 是否可以修改emui3系列手机导航栏
    val navigationBarWithEMUI3Enable: Boolean = true,

    // view透明度
    @FloatRange(from = 0.0, to = 1.0) var viewAlpha: Float? = null,
    // 同步变色的view
    var viewMap: Map<View, Pair<Int, Int>?>? = null
) {

    /**
     * 调整深色亮色模式参数
     */
    fun adjustDarkModeParams() {
        if (autoStatusBarDarkModeEnable && statusBarColor != Color.TRANSPARENT) {
            val statusBarDarkFont = statusBarColor > IMMERSION_BOUNDARY_COLOR
            statusBarDarkFont(statusBarDarkFont, autoStatusBarDarkModeAlpha)
        }
        if (autoNavigationBarDarkModeEnable && navigationBarColor != Color.TRANSPARENT) {
            val navigationBarDarkIcon = navigationBarColor > IMMERSION_BOUNDARY_COLOR
            navigationBarDarkIcon(navigationBarDarkIcon, autoNavigationBarDarkModeAlpha)
        }
    }

    /**
     * 设置状态栏字体为 深色 或 亮色
     * @param isDarkFont true深色 false亮色
     * @param statusBarAlpha 状态栏透明度，在字体不支持变色时使用
     */
    private fun statusBarDarkFont(
        isDarkFont: Boolean,
        @FloatRange(from = 0.0, to = 1.0) statusBarAlpha: Float = 0.2f
    ) {
        this.statusBarDarkFont = isDarkFont
        if (isDarkFont && !isSupportStatusBarDarkFont) {
            this.statusBarAlpha = statusBarAlpha
        } else {
            flymeOSStatusBarFontColor = flymeOSStatusBarFontTempColor
            this.statusBarAlpha = statusBarTempAlpha
        }
    }

    /**
     * 设置导航栏图标为 深色 或 亮色
     * @param isDarkIcon true深色 false亮色
     * @param navigationBarAlpha 导航栏透明度，在图标不支持变色时使用
     */
    private fun navigationBarDarkIcon(
        isDarkIcon: Boolean,
        @FloatRange(from = 0.0, to = 1.0) navigationBarAlpha: Float = 0.2f
    ) {
        navigationBarDarkIcon = isDarkIcon
        if (isDarkIcon && !isSupportNavigationIconDark) {
            this.navigationBarAlpha = navigationBarAlpha
        } else {
            this.navigationBarAlpha = navigationBarTempAlpha
        }
    }


    class Builder(config: BarConfig? = null) {

        private var statusBarColor: Int = config?.statusBarColor ?: Color.TRANSPARENT
        private var statusBarColorTransform: Int = config?.statusBarColorTransform ?: Color.BLACK
        private var statusBarAlpha: Float = config?.statusBarAlpha ?: 0.0f
        private var navigationBarColor: Int = config?.navigationBarColor ?: Color.BLACK
        private var navigationBarColorTransform: Int = config?.navigationBarColorTransform ?: Color.BLACK
        private var navigationBarAlpha: Float = config?.navigationBarAlpha ?: 0.0f
        private var fullScreen: Boolean = config?.fullScreen ?: false
        private var autoStatusBarDarkModeEnable: Boolean = config?.autoStatusBarDarkModeEnable ?: false
        private var autoStatusBarDarkModeAlpha: Float = config?.autoStatusBarDarkModeAlpha ?: 1.0f
        private var autoNavigationBarDarkModeEnable: Boolean = config?.autoNavigationBarDarkModeEnable ?: false
        private var autoNavigationBarDarkModeAlpha: Float = config?.autoNavigationBarDarkModeAlpha ?: 1.0f
        private var statusBarDarkFont: Boolean = config?.statusBarDarkFont ?: false
        private var navigationBarDarkIcon: Boolean = config?.navigationBarDarkIcon ?: false
        private var flymeOSStatusBarFontColor: Int = config?.flymeOSStatusBarFontColor ?: 0
        private var barHideCode: Int = config?.barHideCode ?: BarHide.FLAG_SHOW_BAR
        private var hideNavigationBar: Boolean = config?.hideNavigationBar ?: false
//        private var fits: Boolean = false
//        private var fitsStatusBarType: Int = FitsFlag.DEFAULT
        private var contentColor: Int = config?.contentColor ?: Color.TRANSPARENT
        private var contentColorTransform: Int = config?.contentColorTransform ?: Color.BLACK
        private var contentAlpha: Float = config?.contentAlpha ?: 0.0f
//        private var fitsLayoutOverlapEnable: Boolean = config?.fitsLayoutOverlapEnable ?: true
//        private var statusView: View? = null
//        private var titleBarView: View? = null
        private var statusBarColorEnabled: Boolean = config?.statusBarColorEnabled ?: true
        private var isSupportActionBar: Boolean = config?.isSupportActionBar ?: false
        private var keyboardEnable: Boolean = config?.keyboardEnable ?: false
        private var keyboardMode: Int = config?.keyboardMode ?: DEFAULT_KEYBOARD_MODE
        private var navigationBarEnable: Boolean = config?.navigationBarEnable ?: true
        private var navigationBarWithKitkatEnable: Boolean = config?.navigationBarWithKitkatEnable ?: true
        private var navigationBarWithEMUI3Enable: Boolean = config?.navigationBarWithEMUI3Enable ?: true
        private var viewAlpha: Float? = config?.viewAlpha
        private var viewMap: MutableMap<View, Pair<Int, Int>?>? = config?.viewMap?.toMutableMap()

        /**
         * 透明状态栏和导航栏
         */
        fun transparentBar(): Builder {
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
            fullScreen = true
            return this
        }

        /**
         * 透明状态栏, 默认透明
         */
        fun transparentStatusBar(): Builder {
            statusBarColor = Color.TRANSPARENT
            return this
        }

        /**
         * 透明导航栏, 默认透明
         */
        fun transparentNavigationBar(): Builder {
            navigationBarColor = Color.TRANSPARENT
            fullScreen = true
            return this
        }

        /**
         * 状态栏颜色
         */
        fun statusBarColor(
            @ColorInt color: Int,
            @FloatRange(from = 0.0, to = 1.0) alpha: Float? = null
        ): Builder {
            statusBarColor = color
            alpha?.let { statusBarAlpha = it }
            return this
        }

        /**
         * 导航栏颜色
         */
        fun navigationBarColor(
            @ColorInt color: Int,
            @FloatRange(from = 0.0, to = 1.0) alpha: Float? = null
        ): Builder {
            navigationBarColor = color
            alpha?.let { navigationBarAlpha = it }
            return this
        }

        /**
         * 状态栏根据透明度最后变换成的颜色
         */
        fun statusBarColorTransform(@ColorInt color: Int): Builder {
            statusBarColorTransform = color
            return this
        }

        /**
         * 导航栏根据透明度最后变换成的颜色
         */
        fun navigationBarColorTransform(@ColorInt color: Int): Builder {
            navigationBarColorTransform = color
            return this
        }

        /**
         * 状态栏&导航栏 透明度
         */
        fun barAlpha(
            @FloatRange(from = 0.0, to = 1.0) alpha: Float
        ): Builder {
            statusBarAlpha = alpha
            navigationBarAlpha = alpha
            return this
        }

        /**
         * 状态栏透明度
         */
        fun statusBarAlpha(
            @FloatRange(from = 0.0, to = 1.0) alpha: Float
        ): Builder {
            statusBarAlpha = alpha
            return this
        }


        /**
         * 导航栏透明度
         */
        fun navigationBarAlpha(
            @FloatRange(from = 0.0, to = 1.0) alpha: Float
        ): Builder {
            navigationBarAlpha = alpha
            return this
        }

        /**
         * 有导航栏的情况下，Activity是否全屏显示
         */
        fun fullScreen(isFullScreen: Boolean): Builder {
            fullScreen = isFullScreen
            return this
        }

        /**
         * 自动根据StatusBar和NavigationBar颜色调整 深色模式 与 亮色模式
         */
        fun autoDarkModeEnable(
            enable: Boolean,
            @FloatRange(from = 0.0, to = 1.0) alpha: Float = 0.2f
        ): Builder {
            autoStatusBarDarkModeEnable = enable
            autoStatusBarDarkModeAlpha = alpha
            autoNavigationBarDarkModeEnable = enable
            autoNavigationBarDarkModeAlpha = alpha
            return this
        }

        /**
         * 自动根据StatusBar颜色调整 深色模式 与 亮色模式
         */
        fun autoStatusBarDarkModeEnable(
            enable: Boolean,
            @FloatRange(from = 0.0, to = 1.0) alpha: Float = 0.2f
        ): Builder {
            autoStatusBarDarkModeEnable = enable
            autoStatusBarDarkModeAlpha = alpha
            return this
        }

        /**
         * 自动根据NavigationBar颜色调整 深色模式 与 亮色模式
         */
        fun autoNavigationBarDarkModeEnable(
            enable: Boolean,
            @FloatRange(from = 0.0, to = 1.0) alpha: Float = 0.2f
        ): Builder {
            autoNavigationBarDarkModeEnable = enable
            autoNavigationBarDarkModeAlpha = alpha
            return this
        }

        /**
         * 设置状态栏字体为 深色 或 亮色
         * @param isDarkFont true深色 false亮色
         * @param statusBarAlpha 状态栏透明度，在字体不支持变色时使用
         */
        fun statusBarDarkFont(
            isDarkFont: Boolean,
            @FloatRange(from = 0.0, to = 1.0) statusBarAlpha: Float = 0.2f
        ): Builder {
            statusBarDarkFont = isDarkFont
            if (isDarkFont && !isSupportStatusBarDarkFont) {
                this.statusBarAlpha = statusBarAlpha
            }
            return this
        }

        /**
         * 设置导航栏图标为 深色 或 亮色
         * @param isDarkIcon true深色 false亮色
         * @param navigationBarAlpha 导航栏透明度，在图标不支持变色时使用
         */
        fun navigationBarDarkIcon(
            isDarkIcon: Boolean,
            @FloatRange(from = 0.0, to = 1.0) navigationBarAlpha: Float = 0.2f
        ): Builder {
            navigationBarDarkIcon = isDarkIcon
            if (isDarkIcon && !isSupportNavigationIconDark) {
                this.navigationBarAlpha = navigationBarAlpha
            }
            return this
        }

        /**
         * 修改 Flyme OS系统手机状态栏字体颜色
         * PS: 优先级高于statusBarDarkFont
         */
        fun flymeOSStatusBarFontColor(@ColorInt color: Int): Builder {
            flymeOSStatusBarFontColor = color
            return this
        }

        /**
         * 显示or隐藏 状态栏与导航栏
         */
        fun hideBar(@BarHide code: Int): Builder {
            barHideCode = code
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT
                || isEMUI3x()) {
                hideNavigationBar = when(code) {
                    BarHide.FLAG_HIDE_NAVIGATION_BAR,
                    BarHide.FLAG_HIDE_BAR -> true
                    else -> false
                }
            }
            return this
        }

//        /**
//         * 解决布局与状态栏重叠问题
//         */
//        fun fitsSystemWindows(fits: Boolean): Builder {
//            this.fits = fits
//            if (fits) {
//                if (fitsStatusBarType == FitsFlag.DEFAULT) {
//                    fitsStatusBarType = FitsFlag.SYSTEM_WINDOWS
//                }
//            } else {
//                fitsStatusBarType = FitsFlag.DEFAULT
//            }
//            return this
//        }

//        /**
//         *  解决布局与状态栏重叠问题，支持侧滑返回
//         *  @param contentColor 整体界面背景色
//         *  @param contentColorTransform 整体界面变换后的背景色
//         *  @param contentAlpha 整体界面透明度
//         */
//        fun fitsSystemWindows(
//            fits: Boolean,
//            @ColorInt contentColor: Int,
//            @ColorInt contentColorTransform: Int = Color.BLACK,
//            @FloatRange(from = 0.0, to = 1.0) contentAlpha: Float = 0.0f
//        ): Builder {
//            this.fits = fits
//            this.contentColor = contentColor
//            this.contentColorTransform = contentColorTransform
//            this.contentAlpha = contentAlpha
//            if (fits) {
//                if (fitsStatusBarType == FitsFlag.DEFAULT) {
//                    fitsStatusBarType = FitsFlag.SYSTEM_WINDOWS
//                }
//            } else {
//                fitsStatusBarType == FitsFlag.DEFAULT
//            }
//            // TODO mContentView.setBackgroundColor(ColorUtils.blendARGB(mBarParams.contentColor,
//            //                mBarParams.contentColorTransform, mBarParams.contentAlpha));
//            return this
//        }
//
//        /**
//         * 是否可以修复状态栏与布局重叠，默认为true，
//         * 只适合ImmersionBar#statusBarView，
//         *      ImmersionBar#titleBar，
//         *      ImmersionBar#titleBarMarginTop
//         */
//        fun fitsLayoutOverlapEnable(enable: Boolean): Builder {
//            fitsLayoutOverlapEnable = enable
//            return this
//        }
//
//        /**
//         * 通过状态栏高度动态设置状态栏布局
//         */
//        fun statusBarView(view: View?): Builder {
//            statusView = view
//            if (statusView != null) {
//                if (fitsStatusBarType == FitsFlag.DEFAULT) {
//                    fitsStatusBarType = FitsFlag.STATUS
//                }
//            } else {
//                if (fitsStatusBarType == FitsFlag.STATUS) {
//                    fitsStatusBarType = FitsFlag.DEFAULT
//                }
//            }
//            return this
//        }

//        /**
//         * 解决状态栏与布局顶部重叠又多了种方法
//         * @param statusBarColorTransformEnable 是否可以修改状态栏颜色
//         */
//        fun titleBarView(
//            view: View?,
//            statusBarColorTransformEnable: Boolean = true
//        ): Builder {
//            titleBarView = view
//            if (titleBarView != null) {
//                if (fitsStatusBarType == FitsFlag.DEFAULT
//                    || fitsStatusBarType == FitsFlag.TITLE_MARGIN_TOP) {
//                    fitsStatusBarType = FitsFlag.TITLE
//                }
//            } else {
//                if (fitsStatusBarType == FitsFlag.TITLE
//                    || fitsStatusBarType == FitsFlag.TITLE_MARGIN_TOP) {
//                    fitsStatusBarType = FitsFlag.DEFAULT
//                }
//            }
//            statusBarColorEnabled = statusBarColorTransformEnable
//            return this
//        }

//        /**
//         * 绘制标题栏距离顶部的高度为状态栏的高度
//         */
//        fun titleBarMarginTop(view: View?): Builder {
//            titleBarView = view
//            if (titleBarView != null) {
//                if (fitsStatusBarType == FitsFlag.DEFAULT
//                    || fitsStatusBarType == FitsFlag.TITLE) {
//                    fitsStatusBarType = FitsFlag.TITLE_MARGIN_TOP
//                }
//            } else {
//                if (fitsStatusBarType == FitsFlag.TITLE
//                    || fitsStatusBarType == FitsFlag.TITLE) {
//                    fitsStatusBarType = FitsFlag.DEFAULT
//                }
//            }
//            return this
//        }

        /**
         * 是否可以修改状态栏颜色
         */
        fun statusBarColorTransformEnable(
            statusBarColorTransformEnable: Boolean
        ): Builder {
            statusBarColorEnabled = statusBarColorTransformEnable
            return this
        }

        /**
         * 支持有actionBar的界面,调用该方法，布局讲从actionBar下面开始绘制
         */
        fun supportActionBar(isSupportActionBar: Boolean): Builder {
            this.isSupportActionBar = isSupportActionBar
            return this
        }

        /**
         * 解决软键盘与底部输入框冲突问题 ，默认是false
         */
        fun keyboardEnable(
            enable: Boolean,
            mode: Int = DEFAULT_KEYBOARD_MODE
        ): Builder {
            keyboardEnable = enable
            keyboardMode = mode
            return this
        }

        /**
         * 修改键盘模式
         */
        fun keyboardMode(mode: Int): Builder {
            keyboardMode = mode
            return this
        }

        /**
         * 是否可以修改导航栏颜色，默认为true
         * 优先级 navigationBarEnable  >
         *       navigationBarWithKitkatEnable >
         *       navigationBarWithEMUI3Enable
         */
        fun navigationBarEnable(enable: Boolean): Builder {
            navigationBarEnable = enable
            return this
        }

        /**
         * 是否可以修改4.4设备导航栏颜色，默认为true
         * 优先级 navigationBarEnable  >
         *       navigationBarWithKitkatEnable >
         *       navigationBarWithEMUI3Enable
         */
        fun navigationBarWithKitkatEnable(enable: Boolean): Builder {
            navigationBarWithKitkatEnable = enable
            return this
        }

        /**
         * 是否能修改华为emui3.1导航栏颜色，默认为true
         * 优先级 navigationBarEnable  >
         *       navigationBarWithKitkatEnable >
         *       navigationBarWithEMUI3Enable
         */
        fun navigationBarWithEMUI3Enable(enable: Boolean): Builder {
            if (isEMUI3x()) {
                navigationBarWithEMUI3Enable = true
                navigationBarWithKitkatEnable = true
            }
            return this
        }

        /**
         * view透明度
         */
        fun viewAlpha(
            @FloatRange(from = 0.0, to = 1.0) alpha: Float = 0.0f
        ): Builder {
            viewAlpha = alpha
            return this
        }

        /**
         * 添加变色view
         */
        fun addViewSupportTransformColor(
            view: View,
            @ColorInt viewColorBeforeTransform: Int? = null,
            @ColorInt viewColorAfterTransform: Int? = null
        ): Builder {
            if (viewMap == null) {
                viewMap = HashMap()
            }
            viewMap!![view] = if (viewColorBeforeTransform != null && viewColorAfterTransform != null) {
                viewColorBeforeTransform to viewColorAfterTransform
            } else null
            return this
        }

        /**
         * 删除指定同步变色view
         */
        fun removeSupportView(view: View): Builder {
            viewMap?.remove(view)
            return this
        }

        /**
         * 清除所有同步变色View
         */
        fun removeSupportAllView(): Builder {
            viewMap?.let {
                if (it.isNotEmpty()) {
                    it.clear()
                    viewMap = null
                }
            }
            return this
        }

        fun build(): BarConfig {
            return BarConfig(
                statusBarColor = statusBarColor,
                statusBarColorTransform = statusBarColorTransform,
                statusBarAlpha = statusBarAlpha,
                statusBarTempAlpha = statusBarAlpha,
                navigationBarColor = navigationBarColor,
                navigationBarColorTransform = navigationBarColorTransform,
                navigationBarAlpha = navigationBarAlpha,
                navigationBarTempAlpha = navigationBarAlpha,
                fullScreen = fullScreen,
                autoStatusBarDarkModeEnable = autoStatusBarDarkModeEnable,
                autoStatusBarDarkModeAlpha = autoStatusBarDarkModeAlpha,
                autoNavigationBarDarkModeEnable = autoNavigationBarDarkModeEnable,
                autoNavigationBarDarkModeAlpha = autoNavigationBarDarkModeAlpha,
                statusBarDarkFont = statusBarDarkFont,
                navigationBarDarkIcon = navigationBarDarkIcon,
                flymeOSStatusBarFontColor = flymeOSStatusBarFontColor,
                barHideCode = barHideCode,
                hideNavigationBar = hideNavigationBar,
//                fits = fits,
//                fitsStatusBarType = fitsStatusBarType,
                contentColor = contentColor,
                contentColorTransform = contentColorTransform,
                contentAlpha = contentAlpha,
//                fitsLayoutOverlapEnable = fitsLayoutOverlapEnable,
//                statusBarView = statusView,
//                titleBarView = titleBarView,
                statusBarColorEnabled = statusBarColorEnabled,
                isSupportActionBar = isSupportActionBar,
                keyboardEnable = keyboardEnable,
                keyboardMode = keyboardMode,
                navigationBarEnable = navigationBarEnable,
                navigationBarWithKitkatEnable = navigationBarWithKitkatEnable,
                navigationBarWithEMUI3Enable = navigationBarWithEMUI3Enable,
                viewAlpha = viewAlpha,
                viewMap = viewMap
            )
        }
    }
}