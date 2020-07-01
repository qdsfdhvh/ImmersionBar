package com.seiko.immersionbar.sample.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.seiko.immersionbar.sample.OnSplashListener
import com.seiko.immersionbar.sample.R
import com.seiko.immersionbar.sample.utils.GlideUtils
import com.seiko.immersionbar.sample.utils.Utils
import com.seiko.immersionbar.setTitleBar
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author geyifeng
 * @date 2019-04-22 15:27
 */
class SplashFragment : BaseFragment(), Observer<Long> {

    @BindView(R.id.iv_splash)
    lateinit var ivSplash: ImageView

    @BindView(R.id.tv_time)
    lateinit var tvTime: TextView

    private var mTotalTime: Long = 3
    private var mSubscribe: Disposable? = null
    private var mOnSplashListener: OnSplashListener? = null
    var isFinish = false
        private set

    override fun onDestroy() {
        super.onDestroy()
        stopCountDown()
    }

    override fun initDataBeforeView(savedInstanceState: Bundle?) {
        super.initDataBeforeView(savedInstanceState)
        val arguments = arguments
        if (arguments != null) {
            mTotalTime = arguments.getLong(mKey)
        }
    }

    override val layoutId: Int = R.layout.fragment_splash

    override fun initData() {
        super.initData()
        startCountDown()
    }

    override fun initView() {
        super.initView()
        setTitleBar(tvTime)
        GlideUtils.load(Utils.getFullPic(), ivSplash, R.drawable.pic_all)
    }

    override fun setListener() {
        super.setListener()
        tvTime.setOnClickListener {
            if (mOnSplashListener != null) {
                mOnSplashListener!!.onTime(0, mTotalTime)
            }
            finish()
        }
    }

    /**
     * 关闭当前页面
     */
    private fun finish() {
        if (fragmentManager != null) {
            val fragment = fragmentManager!!.findFragmentByTag(TAG)
            if (fragment != null) {
                fragmentManager!!.beginTransaction()
                    .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                    .remove(fragment)
                    .commitAllowingStateLoss()
            }
            mOnSplashListener = null
        }
        isFinish = true
    }

    /**
     * 开启倒计时
     */
    private fun startCountDown() {
        Observable.interval(1, TimeUnit.SECONDS)
            .map { aLong: Long -> mTotalTime - aLong }
            .take(mTotalTime + 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this)
    }

    /**
     * 关闭倒计时
     */
    private fun stopCountDown() {
        if (mSubscribe != null && !mSubscribe!!.isDisposed) {
            mSubscribe!!.dispose()
            mSubscribe = null
        }
    }

    override fun onSubscribe(d: Disposable) {
        mSubscribe = d
    }

    @SuppressLint("SetTextI18n")
    override fun onNext(aLong: Long) {
        tvTime.text = "我是" + aLong + "s欢迎页，点我可以关闭"
        if (mOnSplashListener != null) {
            mOnSplashListener!!.onTime(aLong, mTotalTime)
        }
    }

    override fun onError(e: Throwable) {
        finish()
    }

    override fun onComplete() {
        finish()
    }

    fun setOnSplashListener(onSplashListener: OnSplashListener?) {
        mOnSplashListener = onSplashListener
    }

    companion object {
        private const val TAG = "SplashFragment"
        private const val mKey = "TotalTime"
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }

        fun newInstance(totalTime: Long): SplashFragment {
            val splashFragment = SplashFragment()
            val bundle = Bundle()
            bundle.putLong(mKey, totalTime)
            splashFragment.arguments = bundle
            return splashFragment
        }
    }
}