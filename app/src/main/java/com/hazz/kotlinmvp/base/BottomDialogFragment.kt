package com.hazz.kotlinmvp.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager

abstract class BottomDialogFragment :BaseDialogFragment() {

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val window = it.window
            val params = window.attributes
            params.gravity = Gravity.BOTTOM
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            window.attributes = params
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}