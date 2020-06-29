package com.gyf.immersionbar.sample.utils

import android.app.Activity
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Activity.getResColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Fragment.getResColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(requireActivity(), id)
}

fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}