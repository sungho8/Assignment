package com.sungho.searchapp.util

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView

object Layout {
    fun roundCorner(view: ImageView, corner: Int = 20) {
        view.outlineProvider = (object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                val radius = corner
                if (view != null) {
                    outline?.setRoundRect(0, 0, view.width, view.height, radius.toFloat())
                }
            }
        })
        view.clipToOutline = true
    }
}