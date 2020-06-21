package com.gyf.immersionbar

import android.annotation.SuppressLint

private const val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
private const val KEY_EMUI_VERSION_NAME = "ro.build.version.emui"
private const val KEY_DISPLAY = "ro.build.display.id"

private fun getSystemProperty(key: String, defaultValue: String = ""): String {
    try {
        @SuppressLint("PrivateApi")
        val clz = Class.forName("android.os.SystemProperties")
        val method = clz.getMethod("get",
                String::class.java, String::class.java)
        return method.invoke(clz, key, defaultValue) as String
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return defaultValue
}

/**
 * 设备是否为MIUI
 */
internal fun isMIUI(): Boolean {
    return getSystemProperty(KEY_MIUI_VERSION_NAME).isNotEmpty()
}

/**
 * 设备是否为MIUI 6+
 */
internal fun isMIUI6(): Boolean {
    val version = getSystemProperty(KEY_MIUI_VERSION_NAME)
    if (version.isEmpty()) {
        return false
    }
    val code = version.toIntOrNull() ?: return false
    return code >= 6
}

/**
 * 设备是否为EMUI
 */
internal fun isEMUI(): Boolean {
    return getSystemProperty(KEY_EMUI_VERSION_NAME).isNotEmpty()
}

/**
 * 设备是否为EMUI 3.1
 */
internal fun isEMUI31(): Boolean {
    val version = getSystemProperty(KEY_EMUI_VERSION_NAME)
    if (version.isEmpty()) {
        return false
    }
    return "EmotionUI 3" == version
            || version.contains("EmotionUI_3.1")
}

/**
 * 设备是否为EMUI 3.0
 */
internal fun isEMUI30(): Boolean {
    val version = getSystemProperty(KEY_EMUI_VERSION_NAME)
    if (version.isEmpty()) {
        return false
    }
    return "EmotionUI_3.0" == version
}

/**
 * 设备是否为EMUI 3.x
 */
internal fun isEMUI3x(): Boolean {
    val version = getSystemProperty(KEY_EMUI_VERSION_NAME)
    if (version.isEmpty()) {
        return false
    }
    return "EmotionUI 3" == version
            || "EmotionUI_3.0" == version
            || version.contains("EmotionUI_3.1")
}

/**
 * 设备是否为FlymeOS
 */
internal fun isFlymeOS(): Boolean {
    return getSystemProperty(KEY_DISPLAY).contains("flyme", ignoreCase = true)
}

/**
 * 设备是否为FlymeOS 4.0+
 */
internal fun isFlymeOS4(): Boolean {
    val version = getSystemProperty(KEY_DISPLAY)
    if (!version.contains("flyme", ignoreCase = true)) {
        return false
    }
    val code = (if (version.contains("os", ignoreCase = true)) {
        version.substring(9, 10)
    } else {
        version.substring(6, 7)
    }).toIntOrNull() ?: return false
    return code >= 4
}
