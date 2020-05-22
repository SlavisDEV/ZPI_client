package io.slavisdev.zpi.ui.base

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.File

@BindingAdapter("visibleIf")
fun changeViewVisibility(view: View, bool: Boolean) {
    if (bool) {
        view.animate().alpha(1.0f)
    } else {
        view.animate().alpha(0.0f)
    }
}

@BindingAdapter("imageFromResource")
fun setImageFromResource(view: ImageView, resource: Int) {
    view.setImageResource(resource)
}

@BindingAdapter("imageFromUri")
fun setImageFromUrl(view: ImageView, url: String?) {
    if (url == null) {
        return
    }

    Glide.with(view)
        .load(url)
        .into(view)
}