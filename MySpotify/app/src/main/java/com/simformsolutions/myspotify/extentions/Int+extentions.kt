package com.simformsolutions.myspotify.extentions

import android.graphics.Color
import kotlin.random.Random

/**
 * Generate random color
 */
fun Int.Companion.randomColor(): Int {
    return Color.argb(
        255,
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256)
    )
}