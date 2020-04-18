package io.slavisdev.zpi.ui.base

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun changeViewVisibility(view: View, bool: Boolean) {
    if (bool) {
        view.animate().alpha(1.0f)
    } else {
        view.animate().alpha(0.0f)
    }
}