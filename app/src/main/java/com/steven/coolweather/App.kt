package com.steven.coolweather

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/25
 */
class App : Application() {

    companion object {
        @JvmStatic
        var mContext: App? = null
            private set

        fun getInstance() = mContext!!
    }


    override fun onCreate() {
        super.onCreate()
        mContext = this
        MMKV.initialize(this)
    }
}