package io.slavisdev.zpi.ui.base

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.io.File

@BindingAdapter("visibleIf")
fun changeViewVisibility(view: View, bool: Boolean) {
    if (bool) {
        view.animate().alpha(1.0f)
    } else {
        view.animate().alpha(0.0f)
    }
}

@BindingAdapter("invisibleIf")
fun hideView(view: View, bool: Boolean) {
    if (bool) {
        view.visibility = View.INVISIBLE
    } else {
        view.visibility = View.VISIBLE
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

    val requestOptions: RequestOptions = RequestOptions().apply {
        transform(CenterCrop())
        transform(RoundedCorners(12))
    }
    Glide.with(view)
        .load(url)
        .apply(requestOptions)
        .into(view)
}