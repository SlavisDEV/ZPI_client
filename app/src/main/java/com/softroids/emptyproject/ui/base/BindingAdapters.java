package com.softroids.emptyproject.ui.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    private BindingAdapters() { }

    @BindingAdapter("visibleIf")
    public static void changeViewVisibility(@NonNull View view, @NonNull Boolean bool) {
        if (bool) {
            view.animate().alpha(1.0f);
        } else {
            view.animate().alpha(0.0f);
        }
    }
}
