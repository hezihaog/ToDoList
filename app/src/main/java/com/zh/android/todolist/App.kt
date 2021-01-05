package com.zh.android.todolist

import android.app.Application
import com.apkfuns.logutils.LogUtils

/**
 * @author wally
 * @date 2021/01/04
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        LogUtils.getLogConfig().configShowBorders(false)
    }
}