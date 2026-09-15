package com.waxd.launcher

import org.fossify.commons.FossifyApp
import com.waxd.launcher.helpers.Config

class WaxdApp : FossifyApp() {
    override fun onCreate() {
        super.onCreate()
        Config.newInstance(this).applyWaxdThemeDefaultsIfNeeded()
    }
}
