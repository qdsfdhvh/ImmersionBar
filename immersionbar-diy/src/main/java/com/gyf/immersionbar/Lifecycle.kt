package com.gyf.immersionbar

import android.util.SparseArray
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*

fun FragmentActivity.immersionBar(builder: BarConfig.Builder.() -> Unit = {}): ImmersionBar {
    return barScope(
        creator = {
            ImmersionBar(
                activity = this,
                builder = builder
            )
        },
        onCreated = { it.bindLifecycle(lifecycle) },
        onUpdate = { it.update(builder) }
    )
}

fun Fragment.immersionBar(builder: BarConfig.Builder.() -> Unit = {}): ImmersionBar {
    return barScope(
        creator = {
            ImmersionBar(
                activity = requireActivity(),
                builder = builder,
                isFragment = true,
                fragment = this,
                parent = requireActivity().immersionBar()
            )
        },
        onCreated = { it.bindLifecycle(lifecycle) },
        onUpdate = { it.update(builder) }
    )
}

fun DialogFragment.immersionBar(builder: BarConfig.Builder.() -> Unit = {}): ImmersionBar {
    return barScope(
        creator = {
            ImmersionBar(
                activity = requireActivity(),
                barConfig = BarConfig.Builder().apply(builder).build(),
                isFragment = true,
                fragment = this,
                isDialog = true,
                dialog = requireDialog(),
                parent = requireActivity().immersionBar()
            )
        },
        onCreated = { it.bindLifecycle(lifecycle) },
        onUpdate = { it.update(builder) }
    )
}

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
    onUpdate: (ImmersionBar) -> Unit
): ImmersionBar {
    val viewModel = ViewModelProvider(this, factory)
            .get(ImmersionBarViewModel::class.java)
    var bar = viewModel.get(barTag)
    if (bar == null) {
        bar = creator()
        viewModel.put(barTag, bar)
        onCreated(bar)
    } else {
        onUpdate(bar)
    }
    return bar
}

fun Fragment.barScope(
    creator: () -> ImmersionBar,
    onCreated: (ImmersionBar) -> Unit,
    onUpdate: (ImmersionBar) -> Unit
): ImmersionBar {
    val viewModel = ViewModelProvider(this, factory)
        .get(ImmersionBarViewModel::class.java)
    var bar = viewModel.get(barTag)
    if (bar == null) {
        bar = creator()
        viewModel.put(barTag, bar)
        onCreated(bar)
    } else {
        onUpdate(bar)
    }
    return bar
}

private val FragmentActivity.barTag: Int
    get() = System.identityHashCode(this)

private val Fragment.barTag: Int
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

    fun put(key: Int, bar: ImmersionBar) {
        map.put(key, bar)
    }

    fun get(key: Int): ImmersionBar? {
        return map[key]
    }

    override fun onCleared() {
        for (i in 0..map.size()) {
            map.valueAt(i).onDestroy()
        }
        map.clear()
    }
}
