package com.dedeandres.covnews.util.ext

import android.view.View

private const val DELAY_IN_MS = 500L

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.avoidDoubleClicks() {
    if (!this.isClickable) {
        return
    }
    this.isClickable = false
    this.postDelayed({ this.isClickable = true }, DELAY_IN_MS)
}