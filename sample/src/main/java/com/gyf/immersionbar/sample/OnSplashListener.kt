package com.gyf.immersionbar.sample

/**
 * The interface On splash listener.
 *
 * @author geyifeng
 * @date 2019 -04-22 16:16
 */
interface OnSplashListener {
    /**
     * On time.
     *
     * @param time      the time
     * @param totalTime the total time
     */
    fun onTime(time: Long, totalTime: Long)
}