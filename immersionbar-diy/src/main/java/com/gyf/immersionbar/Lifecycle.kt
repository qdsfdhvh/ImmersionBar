package com.gyf.immersionbar

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*

val FragmentActivity.immersionBar: ImmersionBar
    get() = barScope {
        ImmersionBar(
            activity = this
        ).apply { bindLifecycle(lifecycle) }
    }

val Fragment.immersionBar: ImmersionBar
    get() = requireActivity().barScope {
        ImmersionBar(
            activity = requireActivity(),
            isFragment = true,
            fragment = this,
            parent = requireActivity().immersionBar
        ).apply { bindLifecycle(lifecycle) }
    }

val DialogFragment.immersionBar: ImmersionBar
    get() = requireActivity().barScope {
        ImmersionBar(
            activity = requireActivity(),
            isFragment = true,
            fragment = this,
            isDialog = true,
            dialog = requireDialog(),
            parent = requireActivity().immersionBar
        ).apply { bindLifecycle(lifecycle) }
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

fun FragmentActivity.barScope(creator: () -> ImmersionBar): ImmersionBar {
    val viewModel = ViewModelProvider(this, factory)
            .get(ImmersionBarViewModel::class.java)
    var bar = viewModel.get(tag)
    if (bar == null) {
        bar = creator()
        viewModel.put(tag, bar)
    }
    return bar
}

private val FragmentActivity.tag: String
    get() = "ImmersionBar" + System.identityHashCode(this)

private val factory by lazy {
    object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ImmersionBarViewModel() as T
        }
    }
}

class ImmersionBarViewModel : ViewModel() {

    private val map = HashMap<String, ImmersionBar>()

    fun put(key: String, bar: ImmersionBar) {
        map.put(key, bar)?.onDestroy()
    }

    fun get(key: String) = map[key]

    override fun onCleared() {
        map.values.forEach { it.onDestroy() }
        map.clear()
    }
}
