package com.simformsolutions.myspotify.extentions

import android.app.Activity
import android.util.TypedValue

/**
 * Get theme color
 */
fun Activity.getThemeColor(resId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(resId, typedValue, true)
    return typedValue.data
}