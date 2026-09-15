package com.waxd.launcher.interfaces

import com.waxd.launcher.models.AppWidget

interface WidgetsFragmentListener {
    fun onWidgetLongPressed(appWidget: AppWidget)
}
