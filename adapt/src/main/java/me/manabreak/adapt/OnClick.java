package me.manabreak.adapt;

import android.support.annotation.NonNull;

public interface OnClick<T> {
    void onClick(@NonNull T item);
}
