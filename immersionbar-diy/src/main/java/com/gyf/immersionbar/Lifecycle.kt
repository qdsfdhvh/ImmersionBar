package com.gyf.immersionbar

import android.app.Dialog
import android.util.Log
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
        creator = { ImmersionBar(activity, builder, window!!) },
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
        creator = { ImmersionBar(fragment.requireActivity(), builder, window!!) },
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
        creator = { ImmersionBar(requireActivity(), builder, requireDialog().window!!) },
        onCreated = { it.bindLifecycle(lifecycle) },
        onUpdate = { it.update(builder) }
    )
}

/**
 * 创建Fragment的ImmersionBar
 */
fun Fragment.immersionBar(builder: BarConfig.Builder.() -> Unit = {}): ImmersionBar {
    return barScope(
        creator = { ImmersionBar(requireActivity(),builder) },
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

fun FragmentActivity.barScope(
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

fun Fragment.barScope(
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

private fun ViewModelStoreOwner.getImmersionBarViewModel(): ImmersionBarViewModel {
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

private class ImmersionBarViewModel : ViewModel() {

    private val map = SparseArray<ImmersionBar>()

    fun put(key: Int, bar: ImmersionBar) {
        map.put(key, bar)
    }

    fun get(key: Int): ImmersionBar? {
        return map[key]
    }

    fun remove(key: Int) {
        map[key]?.onDestroy()
        map.remove(key)
    }

    override fun onCleared() {
        Log.d("ImmersionBarViewModel", "onCleared size = ${map.size()}")
        for (i in 0 until map.size()) {
            map.valueAt(i).onDestroy()
        }
        map.clear()
    }
}