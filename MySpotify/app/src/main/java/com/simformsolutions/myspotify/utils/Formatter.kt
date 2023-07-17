package com.simformsolutions.myspotify.utils

import java.text.NumberFormat
import java.util.Locale

object Formatter {

    @JvmStatic
    fun formatNumber(number: Int): String {
        return NumberFormat.getNumberInstance().format(number)
    }
}