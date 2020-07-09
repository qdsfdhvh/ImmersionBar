package com.seiko.immersionbar

import android.app.Dialog
import android.util.SparseArray
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*

/**
 * 注销Dialog的ImmersionBar
 * PS:Dialog关闭时需要主动注销
 */
fun Dialog.destroyBar(activity: FragmentActivity) {
    val viewModel = activity.getImmersionBarViewModel()
    viewModel.remove(barTag)
}

fun Dialog.destroyBar(fragment: Fragment) {
    val viewModel = fragment.getImmersionBarViewModel()
    viewModel.remove(barTag)
}

/**
 * 创建Dialog的ImmersionBar
 */
fun Dialog.immersionBar(
    activity: FragmentActivity,
    builder: BarConfig.Builder.() -> Unit
): ImmersionBar {
    return activity.barScope(
        creator = {
            val parentBarConfig = activity.barConfig()
            val barConfig = BarConfig.Builder(parentBarConfig).apply(builder).build()
            ImmersionBar(activity, barConfig, window!!)
        },
        onCreated = { it.bindLifecycle(activity.lifecycle) },
        onUpdate = { it.update(builder) },
        tag = this.barTag
    )
}

fun Dialog.immersionBar(
    fragment: Fragment,
    builder: BarConfig.Builder.() -> Unit
): ImmersionBar {
    return fragment.barScope(
        creator = {
            val parentBarConfig = fragment.barConfig()
            val barConfig = BarConfig.Builder(parentBarConfig).apply(builder).build()
            ImmersionBar(fragment.requireActivity(), barConfig, window!!)
        },
        onCreated = { it.bindLifecycle(fragment.lifecycle) },
        onUpdate = { it.update(builder) },
        tag = this.barTag
    )
}

/**
 * 创建DialogFragment的ImmersionBar
 */
fun DialogFragment.immersionBar(builder: BarConfig.Builder.() -> Unit = {}): ImmersionBar {
    return barScope(
        creator = {
            val parentBarConfig = parentFragment?.barConfig()
            val barConfig = BarConfig.Builder(parentBarConfig).apply(builder).build()
            ImmersionBar(requireActivity(), barConfig, requireDialog().window!!)
        },
        onCreated = { it.bindLifecycle(lifecycle) },
        onUpdate = { it.update(builder) }
    )
}

/**
 * 创建Fragment的ImmersionBar
 */
fun Fragment.immersionBar(builder: BarConfig.Builder.() -> Unit = {}): ImmersionBar {
    return barScope(
        creator = { ImmersionBar(requireActivity(), builder) },
        onCreated = { it.bindLifecycle(lifecycle) },
        onUpdate = { it.update(builder) }
    )
}

/**
 * 创建FragmentActivity的ImmersionBar
 */
fun FragmentActivity.immersionBar(builder: BarConfig.Builder.() -> Unit = {}): ImmersionBar {
    return barScope(
        creator = { ImmersionBar(this, builder) },
        onCreated = { it.bindLifecycle(lifecycle) },
        onUpdate = { it.update(builder) }
    )
}

/**
 * 为ImmersionBar绑定生命周期
 */
fun ImmersionBar.bindLifecycle(lifecycle: Lifecycle) {
    lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            when(event) {
                Lifecycle.Event.ON_CREATE -> {
                    onCreate()
                }
                Lifecycle.Event.ON_RESUME -> {
                    onResume()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    onPause()
                }
                Lifecycle.Event.ON_DESTROY -> {
                    onDestroy()
                    lifecycle.removeObserver(this)
                }
                else -> {}
            }
        }
    })
}

/**
 * 尝试获取配置FragmentActivity的BarConfig配置
 */
fun FragmentActivity.barConfig(tag: Int = barTag): BarConfig? {
    val viewModel = getImmersionBarViewModel()
    return viewModel.get(tag)?.barConfig
}

/**
 * 尝试获取配置Fragment的BarConfig配置
 */
fun Fragment.barConfig(tag: Int = barTag): BarConfig? {
    val viewModel = getImmersionBarViewModel()
    return viewModel.get(tag)?.barConfig ?: requireActivity().barConfig()
}

fun ViewModelStoreOwner.barScope(
    creator: () -> ImmersionBar,
    onCreated: (ImmersionBar) -> Unit,
    onUpdate: (ImmersionBar) -> Unit,
    tag: Int = barTag
): ImmersionBar {
    val viewModel = getImmersionBarViewModel()
    var bar = viewModel.get(tag)
    if (bar == null) {
        bar = creator()
        viewModel.put(tag, bar)
        onCreated(bar)
    } else {
        onUpdate(bar)
    }
    return bar
}

fun ViewModelStoreOwner.getImmersionBarViewModel(): ImmersionBarViewModel {
    return ViewModelProvider(this, factory)
        .get(ImmersionBarViewModel::class.java)
}

private val Any.barTag: Int
    get() = System.identityHashCode(this)

private val factory by lazy {
    object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ImmersionBarViewModel() as T
        }
    }
}

class ImmersionBarViewModel : ViewModel() {

    private val map = SparseArray<ImmersionBar>()

    fun put(key: Int, bar: ImmersionBar) = map.put(key, bar)

    fun get(key: Int): ImmersionBar? = map[key]

    fun remove(key: Int) {
        map[key]?.onDestroy()
        map.remove(key)
    }

    override fun onCleared() {
        for (i in 0 until map.size()) {
            map.valueAt(i).onDestroy()
        }
        map.clear()
    }
}