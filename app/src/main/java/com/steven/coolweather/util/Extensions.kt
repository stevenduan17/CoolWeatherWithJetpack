package com.steven.coolweather.util

import android.content.Context
import android.widget.Toast

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */

fun Context.toast(msg: String?) {
    Toast.makeText(this.applicationContext, msg, Toast.LENGTH_SHORT).show()
}