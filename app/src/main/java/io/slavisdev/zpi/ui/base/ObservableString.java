package io.slavisdev.zpi.ui.base;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

public class ObservableString extends ObservableField<String> {
    public ObservableString() {
        super();
    }

    public ObservableString(@Nullable String text) {
        super(text);
    }
}
